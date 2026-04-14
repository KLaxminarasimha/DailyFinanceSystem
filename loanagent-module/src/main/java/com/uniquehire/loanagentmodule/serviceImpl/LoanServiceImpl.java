package com.uniquehire.loanagentmodule.serviceImpl;

import com.uniquehire.loanagentmodule.client.CustomerClient;
import com.uniquehire.loanagentmodule.client.PlanClient;
import com.uniquehire.loanagentmodule.dto.Request.LoanRequestDTO;
import com.uniquehire.loanagentmodule.dto.Response.LoanResponseDTO;
import com.uniquehire.loanagentmodule.entity.Agent;
import com.uniquehire.loanagentmodule.entity.Loan;
import com.uniquehire.loanagentmodule.enums.LoanStatus;
import com.uniquehire.loanagentmodule.exceptions.ResourceNotFoundException;
import com.uniquehire.loanagentmodule.repository.AgentRepository;
import com.uniquehire.loanagentmodule.repository.LoanRepository;
import com.uniquehire.loanagentmodule.service.LoanService;
import com.uniquehire.loanagentmodule.utils.LoanCalculator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final AgentRepository agentRepository;
    private final LoanCalculator loanCalculator;

    private final CustomerClient customerClient;
    private final PlanClient planClient;

    @Override
    public LoanResponseDTO createLoan(LoanRequestDTO request) {

        // verify external services
        customerClient.getCustomer(request.getCustomerId());
        planClient.getPlan(request.getPlanId());

        Agent agent = agentRepository.findById(request.getAgentId())
                .orElseThrow(() -> new ResourceNotFoundException("Agent not found"));

        LocalDate startDate = LocalDate.now();

        Loan loan = Loan.builder()
                .customerId(request.getCustomerId())
                .planId(request.getPlanId())
                .agent(agent)
                .totalAmount(request.getTotalAmount())
                .advance(request.getAdvance())
                .givenAmount(
                        loanCalculator.calculateGivenAmount(
                                request.getTotalAmount(),
                                request.getAdvance()
                        )
                )
                .dailyEmi(request.getDailyEmi())
                .days(request.getDays())
                .startDate(startDate)
                .endDate(
                        loanCalculator.calculateEndDate(
                                startDate,
                                request.getDays()
                        )
                )
                .status(LoanStatus.ACTIVE)
                .overdueDays(0)
                .build();

        Loan savedLoan = loanRepository.save(loan);

        agent.setAssignedLoans(agent.getAssignedLoans() + 1);
        agentRepository.save(agent);

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

    private LoanResponseDTO mapToResponse(Loan loan) {

        LoanResponseDTO response = new LoanResponseDTO();

        response.setLoanId(loan.getLoanId());
        response.setCustomerId(loan.getCustomerId());
        response.setPlanId(loan.getPlanId());
        response.setAgentId(loan.getAgent().getAgentId());
        response.setTotalAmount(loan.getTotalAmount());
        response.setAdvance(loan.getAdvance());
        response.setGivenAmount(loan.getGivenAmount());
        response.setDailyEmi(loan.getDailyEmi());
        response.setDays(loan.getDays());
        response.setStartDate(loan.getStartDate());
        response.setEndDate(loan.getEndDate());
        response.setStatus(loan.getStatus());
        response.setOverdueDays(loan.getOverdueDays());
        response.setTotalFine(loan.getTotalFine());

        return response;
    }
}
