package com.uniquehire.TransactionAndReport.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    private Long referenceId; // loanId

    private Long customerId;

    private BigDecimal amount;

    private String type;
    // LOAN_DISBURSE
    // EMI
    // FINE

    private String direction;
    // DEBIT (money out)
    // CREDIT (money in)

    private String status; // SUCCESS

    private LocalDateTime timestamp;
}