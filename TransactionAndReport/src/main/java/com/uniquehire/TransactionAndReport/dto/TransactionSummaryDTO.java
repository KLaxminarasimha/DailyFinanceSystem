package com.uniquehire.TransactionAndReport.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionSummaryDTO {

    private BigDecimal totalCredit;
    private BigDecimal totalDebit;

    private BigDecimal profit; // credit - debit
}