package com.uniquehire.TransactionAndReport.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanReportDto {
    @NotNull
    private Long longId;

    @NotNull
    private Long customerId;
    @NotBlank
    @Size(min = 2, max = 100)
    private String customerName;

    @NotBlank
    private String planName;

    @DecimalMin(value = "00")
    private BigDecimal totalAmount;

    @DecimalMin(value ="0.0")
    private BigDecimal advance;

    @DecimalMin(value ="0.0")
    private BigDecimal givenAmount;

    @DecimalMin(value="0.0")
    private BigDecimal amountPaid;

    @DecimalMin(value = "0.0")
    private BigDecimal remainingAmount;

    @DecimalMin(value = "0.0")
    private BigDecimal dailyEmi;

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;

    @NotNull
    private LocalDate status;

    @Min(0)
    private Integer overdueDays;

    @DecimalMin(value = "0.0")
    private BigDecimal totalFine;

    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    private Double percentageCompleted;

}
