package com.dailyfinance.fund_service.service;

import com.dailyfinance.fund_service.dto.FundResponseDTO;

import java.math.BigDecimal;

public interface FundService {

    FundResponseDTO getBalance();

    void loanDisbursement(BigDecimal amount, Long loanId);

    void emiPayment(BigDecimal amount, Long loanId);

    void penalty(BigDecimal amount, Long loanId);

    void interest(BigDecimal amount, Long loanId);
}