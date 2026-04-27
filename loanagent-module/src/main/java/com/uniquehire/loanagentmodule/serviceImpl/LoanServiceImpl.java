package com.uniquehire.loanagentmodule.serviceImpl;

import com.uniquehire.loanagentmodule.dto.Request.FundRequestDTO;
import com.uniquehire.loanagentmodule.dto.Response.CustomerDTO;
import com.uniquehire.loanagentmodule.dto.Response.LoanResponseDTO;
import com.uniquehire.loanagentmodule.dto.Response.PlanResponseDTO;
import com.uniquehire.loanagentmodule.dto.Request.TransactionRequestDTO;
import com.uniquehire.loanagentmodule.entity.Loan;
import com.uniquehire.loanagentmodule.repository.LoanRepository;
import com.uniquehire.loanagentmodule.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final RestTemplate restTemplate;

    // 🔥 CREATE LOAN
    @Override
    public LoanResponseDTO createLoan(Long userId, Long planId) {

        // 🔥 STEP 1: GET CUSTOMER FROM CUSTOMER SERVICE
        CustomerDTO customer = restTemplate.getForObject(
                "http://customer-service/customers/user/" + userId,
                CustomerDTO.class
        );

        if (customer == null) {
            throw new RuntimeException("Customer not found");
        }

        Long customerId = customer.getId();

        // 1️⃣ CALL PLAN SERVICE
        PlanResponseDTO plan = restTemplate.getForObject(
                "http://plan-service/plans/" + planId,
                PlanResponseDTO.class
        );

        if (plan == null) {
            throw new RuntimeException("Plan not found");
        }

        // 2️⃣ CALCULATE
        BigDecimal planAmount = plan.getPlanAmount();
        BigDecimal disbursed = planAmount.multiply(BigDecimal.valueOf(0.9));

        // 3️⃣ CREATE LOAN ENTITY
        Loan loan = Loan.builder()
                .customerId(customerId) // ✅ NOW SECURE
                .planId(planId)
                .planAmount(planAmount)
                .disbursedAmount(disbursed)
                .remainingAmount(planAmount)
                .dailyEmi(plan.getDailyEmi())
                .totalDays(plan.getDuration())
                .remainingDays(plan.getDuration())
                .startDate(LocalDate.now())
                .status("ACTIVE")
                .dueAmount(BigDecimal.ZERO)
                .fineAmount(BigDecimal.ZERO)
                .build();

        Loan savedLoan = loanRepository.save(loan);

        // 🔥 4️⃣ CALL FUND SERVICE
        callFundService(disbursed, savedLoan.getLoanId());

        // 🔥 5️⃣ CALL TRANSACTION SERVICE
        callTransactionService(
                savedLoan.getLoanId(),
                customerId,
                disbursed
        );

        return mapToResponse(savedLoan);
    }

    // 🔥 FUND SERVICE CALL
    private void callFundService(BigDecimal amount, Long loanId) {

        FundRequestDTO request = new FundRequestDTO();
        request.setAmount(amount);
        request.setReferenceId(loanId);

        restTemplate.postForObject(
                "http://fund-service/fund/loan",
                request,
                String.class
        );
    }

    // 🔥 GET LOAN
    @Override
    public LoanResponseDTO getLoan(Long id) {

        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        return mapToResponse(loan);
    }

    // 🔥 GET ALL LOANS
    @Override
    public List<LoanResponseDTO> getAllLoans() {

        return loanRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // 🔥 GET BY CUSTOMER
    @Override
    public List<LoanResponseDTO> getLoansByCustomerId(Long customerId) {

        return loanRepository.findByCustomerId(customerId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // 🔥 UPDATE STATUS
    @Override
    public void updateLoanStatus(Long loanId, String status, String remarks) {

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        loan.setStatus(status);

        loanRepository.save(loan);
    }

    @Override
    public void updateAfterPayment(Long loanId,
                                   BigDecimal paid,
                                   BigDecimal due,
                                   BigDecimal fine) {

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        // 🔥 1. CALCULATE NEW REMAINING
        BigDecimal newRemaining = loan.getRemainingAmount().subtract(paid);

        // prevent negative
        if (newRemaining.compareTo(BigDecimal.ZERO) < 0) {
            newRemaining = BigDecimal.ZERO;
        }

        loan.setRemainingAmount(newRemaining);

        // 🔥 2. UPDATE DUE + FINE
        loan.setDueAmount(due);
        loan.setFineAmount(fine);

        // 🔥 3. REDUCE DAYS
        loan.setRemainingDays(loan.getRemainingDays() - 1);

        // 🔥 4. STATUS LOGIC (FIXED)
        if (newRemaining.compareTo(BigDecimal.ZERO) == 0) {
            loan.setStatus("CLOSED");
        } else {
            loan.setStatus("ACTIVE");
        }

        loanRepository.save(loan);

        System.out.println("💰 Payment applied → Loan updated");
    }

    // 🔥 MAPPING METHOD
    private LoanResponseDTO mapToResponse(Loan loan) {

        LoanResponseDTO res = new LoanResponseDTO();

        res.setLoanId(loan.getLoanId());
        res.setCustomerId(loan.getCustomerId());
        res.setPlanId(loan.getPlanId());
        res.setPlanAmount(loan.getPlanAmount());
        res.setDisbursedAmount(loan.getDisbursedAmount());
        res.setRemainingAmount(loan.getRemainingAmount());
        res.setDailyEmi(loan.getDailyEmi());
        res.setTotalDays(loan.getTotalDays());
        res.setRemainingDays(loan.getRemainingDays());
        res.setStartDate(loan.getStartDate());
        res.setStatus(loan.getStatus());
        res.setDueAmount(loan.getDueAmount());
        res.setFineAmount(loan.getFineAmount());

        return res;
    }
    private void callTransactionService(Long loanId,
                                        Long customerId,
                                        BigDecimal amount) {

        TransactionRequestDTO tx = new TransactionRequestDTO(
                loanId,
                customerId,
                amount,
                "LOAN_DISBURSE",
                "DEBIT"
        );

        restTemplate.postForObject(
                "http://transaction-service/transactions",
                tx,
                String.class
        );

    }
    @Override
    public List<LoanResponseDTO> getLoansByUserId(Long userId) {

        // 1️⃣ Call customer-service
        String url = "http://customer-service/customers/user/" + userId;

        Map customer = restTemplate.getForObject(url, Map.class);

        if (customer == null) {
            throw new RuntimeException("Customer not found");
        }

        Long customerId = Long.valueOf(customer.get("id").toString());

        // 2️⃣ Fetch loans using customerId
        return loanRepository.findByCustomerId(customerId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
}