package com.uniquehire.loanagentmodule.dto.Response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class LoanResponseDTO {

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

    private String status;
    private BigDecimal dueAmount;
    private BigDecimal fineAmount;
}