package com.uniquehire.TransactionAndReport.dto.Extra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanCompletedReportDto {
    private Long loanId;
    private Integer totalDays;
    private BigDecimal totalPaid;
    private BigDecimal totalFine;
    private BigDecimal profit;
    private String status;
}
