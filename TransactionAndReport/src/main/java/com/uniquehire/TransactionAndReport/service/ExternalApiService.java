package com.uniquehire.TransactionAndReport.service;

import com.uniquehire.TransactionAndReport.dto.*;
import com.uniquehire.TransactionAndReport.dto.ExternalAgentDto;

import java.util.List;

public interface ExternalApiService {

    List<ExternalPaymentDto> getAllPayments();
    ExternalPaymentDto getPaymentById(Long paymentId);

    List<ExternalLoanDto> getAllLoans();
    ExternalLoanDto getLoanById(Long loanId);

    List<ExternalAgentDto> getAllAgents();
    ExternalAgentDto getAgentById(Long agentId);

    ExternalCustomerDto getCustomerById(Long customerId);

    List<ExternalPlanDto> getAllPlans();
}