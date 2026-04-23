package com.uniquehire.TransactionAndReport.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanReportItemDto {
    private Long loanId;
    private Long customerId;
    private String customerName;
    private Long agentId;
    private String agentName;
    private String planName;
    private BigDecimal totalAmount;
    private BigDecimal advance;
    private BigDecimal givenAmount;
    private BigDecimal amountPaid;
    private BigDecimal remainingAmount;
    private BigDecimal dailyEmi;
    private LocalDate startDate;
    private String status;
    private Integer overdueDays;
    private BigDecimal totalFine;
    private Double percentageCompleted;
}
