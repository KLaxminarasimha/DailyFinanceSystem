package com.uniquehire.TransactionAndReport.dto;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectionReportDto {
    @NotNull
    private Long agentId;
    @NotBlank
    @Size(min = 2, max = 100)
    private String agentName;

    @NotBlank
    private String area;

    @Min(0)
    private Integer assignedLoans;

    @DecimalMin(value = "0.0")
    private BigDecimal collectionTarget;

    @DecimalMin(value = "0.0")
    private BigDecimal amountCollected;

    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    private Double targetAchievement;

    @DecimalMin(value = "0.0" )
    private BigDecimal pendingAmount;

    @DecimalMin(value = "0.0")
    private BigDecimal overdueAmount;

    @DecimalMin(value = "0.0")
    private BigDecimal commissionEarned;

}
