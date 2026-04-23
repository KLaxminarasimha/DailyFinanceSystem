package com.uniquehire.loanagentmodule.serviceImpl;

import com.uniquehire.loanagentmodule.dto.Response.CustomerResponseDTO;
import com.uniquehire.loanagentmodule.dto.Response.LoanResponseDTO;
import com.uniquehire.loanagentmodule.dto.Response.PlanResponseDTO;
import com.uniquehire.loanagentmodule.entity.Loan;
import com.uniquehire.loanagentmodule.enums.LoanStatus;
import com.uniquehire.loanagentmodule.exceptions.ResourceNotFoundException;

import com.uniquehire.loanagentmodule.repository.LoanRepository;
import com.uniquehire.loanagentmodule.service.LoanService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;

    private final RestTemplate restTemplate;

    @Value("${services.customer-service.url}")
    private String customerServiceUrl;

    @Value("${services.plan-service.url}")
    private String planServiceUrl;

    @Override
    public LoanResponseDTO createLoan(Long customerid,Long planid) {


        CustomerResponseDTO customer =
                restTemplate.getForObject(
                        customerServiceUrl + "/" + customerid,
                        CustomerResponseDTO.class
                );

        PlanResponseDTO plan = restTemplate.getForObject(
                planServiceUrl + "/" + planid,
                PlanResponseDTO.class
        );

        if (customer == null) {
            throw new ResourceNotFoundException("Customer not found");
        }

        if (plan == null) {
            throw new ResourceNotFoundException("Plan not found");
        }


//        // TEMPORARY DATA FOR TESTING (remove later)
//
//        CustomerResponseDTO customer = new CustomerResponseDTO();
//        customer.setCustomerId(customerid);
//        customer.setCustomername("Test Customer");
//
//        PlanResponseDTO plan = new PlanResponseDTO();
//        plan.setPlanId(planid);
//        plan.setPlanName("TEST PLAN");
//        plan.setDays(100);
//        plan.setAdvance(BigDecimal.valueOf(2000));
//        plan.setDailyEmi(BigDecimal.valueOf(202));
//        plan.setGivenAmount(BigDecimal.valueOf(18000));
//        plan.setTotalAmount(BigDecimal.valueOf(20000));
//
//        //

        LocalDate startDate = LocalDate.now();
        Loan loan = Loan.builder()
                .customerId(customer.getCustomerId())
                .customerName(customer.getCustomername())
                .planId(plan.getPlanId())
                .planName(plan.getPlanName())
                .days(plan.getDays())
                .advance(plan.getAdvance())
                .dailyEmi(plan.getDailyEmi())
                .givenAmount(plan.getGivenAmount())
                .totalAmount(plan.getTotalAmount())
                .startDate(startDate)
                .endDate(startDate.plusDays(100))
                .status(LoanStatus.ACTIVE)
                .build();

        Loan savedLoan = loanRepository.save(loan);

        return mapToResponse(savedLoan);
    }

    @Override
    public LoanResponseDTO getLoan(Long id) {

        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));

        return mapToResponse(loan);
    }

    @Override
    public List<LoanResponseDTO> getAllLoans() {

        return loanRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void updateLoanStatus(Long loanId, String status, String remarks) {

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));

        LoanStatus loanStatus = LoanStatus.valueOf(status.toUpperCase());

        loan.setStatus(loanStatus);

        loanRepository.save(loan);
    }

    @Override
    public List<LoanResponseDTO> getLoansByCustomerId(Long customerId) {

        List<Loan> loans = loanRepository.findByCustomerId(customerId);

        if (loans.isEmpty()) {
            throw new ResourceNotFoundException("No loans found for this customer");
        }

        return loans.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<LoanResponseDTO> getLoansByStatus(String status) {

        LoanStatus loanStatus = LoanStatus.valueOf(status.toUpperCase());

        List<Loan> loans = loanRepository.findByStatus(loanStatus);

        if (loans.isEmpty()) {
            throw new ResourceNotFoundException("No loans found with this status");
        }

        return loans.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private LoanResponseDTO mapToResponse(Loan loan) {

        LoanResponseDTO response = new LoanResponseDTO();

        response.setLoanId(loan.getLoanId());
        response.setCustomerName(loan.getCustomerName());
        response.setPlanName(loan.getPlanName());
        response.setTotalAmount(loan.getTotalAmount());
        response.setAdvance(loan.getAdvance());
        response.setGivenAmount(loan.getGivenAmount());
        response.setDailyEmi(loan.getDailyEmi());
        response.setDays(loan.getDays());
        response.setStartDate(loan.getStartDate());
        response.setEndDate(loan.getEndDate());
        response.setStatus(loan.getStatus());


        return response;
    }
}
