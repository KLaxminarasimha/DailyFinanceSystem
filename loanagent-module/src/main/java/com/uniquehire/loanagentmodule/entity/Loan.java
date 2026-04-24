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

    private Integer totalDays;
    private Integer remainingDays;

    private LocalDate startDate;

    private String status; // ACTIVE, CLOSED
}