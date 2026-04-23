package com.uniquehire.TransactionAndReport.dto.Extra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDashboardDto {
    private Integer totalLoans;
    private BigDecimal totalDisbursed;
    private BigDecimal advanceProfit;
    private BigDecimal emiCollected;
    private BigDecimal fineCollected;
    private Integer overdueLoans;
    private Integer defaultLoans;
}
