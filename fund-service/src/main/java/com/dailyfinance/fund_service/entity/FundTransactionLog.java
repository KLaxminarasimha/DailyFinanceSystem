package com.dailyfinance.fund_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class FundTransactionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;     // CREDIT / DEBIT
    private String reason;   // EMI / LOAN / PENALTY / INTEREST

    private BigDecimal amount;
    private BigDecimal balanceAfter;

    private Long referenceId; // loanId

    private LocalDateTime timestamp;
}