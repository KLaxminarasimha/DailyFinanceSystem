package com.uniquehire.TransactionAndReport.service;

import com.uniquehire.TransactionAndReport.dto.External.ExternalCustomerDto;
import com.uniquehire.TransactionAndReport.dto.External.ExternalLoanDto;
import com.uniquehire.TransactionAndReport.dto.External.ExternalPaymentDto;
import com.uniquehire.TransactionAndReport.dto.External.ExternalPlanDto;
import com.uniquehire.TransactionAndReport.dto.External.ExternalAgentDto;

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