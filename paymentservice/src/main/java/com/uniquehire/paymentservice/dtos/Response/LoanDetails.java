package com.uniquehire.paymentservice.dtos.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanDetails {
    private Long loanId;
    private String loanStatus;
    private BigDecimal dailyEmi;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal amountPaid;
    private BigDecimal totalFine;
    private BigDecimal totalAmount;
}
