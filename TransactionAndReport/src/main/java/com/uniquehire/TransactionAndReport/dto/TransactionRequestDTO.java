package com.uniquehire.TransactionAndReport.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequestDTO {

    private Long referenceId;   // loanId
    private Long customerId;

    private BigDecimal amount;

    private String type;        // EMI / FINE / LOAN_DISBURSE
    private String direction;   // CREDIT / DEBIT
}