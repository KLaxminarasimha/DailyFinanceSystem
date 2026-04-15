package com.uniquehire.TransactionAndReport.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportSummaryDto {
    @Min(0)
    private Integer totalLoans;

    @Min(0)
    private Integer activeLoans;

    @Min(0)
    private Integer closedLoans;

    @Min(0)
    private Integer defaultedLoans;

    @DecimalMin(value = "0.0")
    private BigDecimal totalCollection;

    @DecimalMin(value = "0.0")
    private BigDecimal pendingAmount;

    @DecimalMin(value = "0.0")
    private BigDecimal overdueAmount;
    @DecimalMin(value = "0.0")
    private BigDecimal totalFineDue;

    @DecimalMin(value= "0.0")
    private BigDecimal totalFinesPaid;

    @DecimalMin(value ="0.0")
    private BigDecimal totalFinesWaived;

    @Min(0)
    private Integer totalAgents;

    private Long topAgentId;

    @Size(min = 2, max= 100)
    private String topAgentName;

    @DecimalMin(value = "0.0")
    private BigDecimal topAgentCollection;

    @NotNull
    private LocalDateTime generatedAt;
}
