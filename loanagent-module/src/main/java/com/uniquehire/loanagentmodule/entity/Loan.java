package com.uniquehire.loanagentmodule.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "loans")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loanId;

    private Long customerId;
    private Long planId;

    private BigDecimal planAmount;
    private BigDecimal disbursedAmount;
    private BigDecimal remainingAmount;

    private BigDecimal dailyEmi;

    private Integer totalDays;       // total duration
    private Integer remainingDays;   // pending days

    private LocalDate startDate;

    @Column(nullable = false)
    private BigDecimal dueAmount = BigDecimal.ZERO;

    @Column(nullable = false)
    private BigDecimal fineAmount = BigDecimal.ZERO;

    private String status; // ACTIVE, CLOSED
}