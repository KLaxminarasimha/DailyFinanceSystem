package com.uniquehire.loanagentmodule.serviceImpl;

import com.uniquehire.loanagentmodule.dto.Request.FundRequestDTO;
import com.uniquehire.loanagentmodule.dto.Response.LoanResponseDTO;
import com.uniquehire.loanagentmodule.dto.Response.PlanResponseDTO;
import com.uniquehire.loanagentmodule.entity.Loan;
import com.uniquehire.loanagentmodule.repository.LoanRepository;
import com.uniquehire.loanagentmodule.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final RestTemplate restTemplate;

    // 🔥 CREATE LOAN
    @Override
    public LoanResponseDTO createLoan(Long customerId, Long planId) {

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
                .customerId(customerId)
                .planId(planId)
                .planAmount(planAmount)
                .disbursedAmount(disbursed)
                .remainingAmount(planAmount)
                .dailyEmi(plan.getDailyEmi())
                .totalDays(plan.getDuration())
                .remainingDays(plan.getDuration())
                .startDate(LocalDate.now())
                .status("ACTIVE")
                .build();

        Loan savedLoan = loanRepository.save(loan);

        // 🔥 4️⃣ CALL FUND SERVICE (DEDUCT MONEY)
        callFundService(disbursed, savedLoan.getLoanId());

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

        return res;
    }
}