package com.uniquehire.loanagentmodule.entity;

import com.uniquehire.loanagentmodule.enums.LoanStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "loans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loanId;

    @Column(nullable = false)
    private Long customerId;

    @Column(nullable = false)
    private Long planId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_id", nullable = false)
    @JsonIgnore
    private Agent agent;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal advance;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal givenAmount;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal dailyEmi;

    @Column(nullable = false)
    private Integer days;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoanStatus status;

    @Column(nullable = false)
    private Integer overdueDays = 0;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalFine = BigDecimal.ZERO;

}