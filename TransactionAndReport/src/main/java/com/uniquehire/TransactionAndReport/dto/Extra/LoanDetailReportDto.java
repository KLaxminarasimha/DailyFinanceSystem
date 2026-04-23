package com.uniquehire.TransactionAndReport.dto.Extra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanDetailReportDto {
    private Long loanId;
    private String customerName;
    private String agentName;
    private String planName;
    private BigDecimal loanAmount;
    private BigDecimal disbursedAmount;
    private BigDecimal emiCollected;
    private BigDecimal fineCollected;
    private BigDecimal remainingEmi;
    private String daysDone;
    private String status;
}
