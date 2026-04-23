package com.uniquehire.TransactionAndReport.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectionReportItemDto {
    private Long agentId;
    private String agentName;
    private String area;
    private Integer assignedLoans;
    private BigDecimal collectionTarget;
    private BigDecimal amountCollected;
    private Double targetAchievement;
    private BigDecimal pendingAmount;
    private BigDecimal overdueAmount;
    private BigDecimal commissionEarned;
}
