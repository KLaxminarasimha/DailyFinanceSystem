package com.uniquehire.TransactionAndReport.dto.External;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExternalLoanDto {
    private Long loanId;
    private Long customerId;
    private String customerName;
    private String customerPhone;
    private String customerEmail;
    private Long planId;
    private String planName;
    private Long agentId;
    private String agentName;
    private BigDecimal totalAmount;
    private BigDecimal advance;
    private BigDecimal givenAmount;
    private BigDecimal dailyEmi;
    private Integer days;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;

    @JsonProperty("overduedays")
    private Integer overduedays;

    private BigDecimal totalFine;
    private BigDecimal amountPaid;
    private BigDecimal remainingAmount;
}