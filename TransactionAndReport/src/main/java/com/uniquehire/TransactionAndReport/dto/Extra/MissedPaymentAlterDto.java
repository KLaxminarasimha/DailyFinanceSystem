package com.uniquehire.TransactionAndReport.dto.Extra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MissedPaymentAlterDto {
    private Long loanId;
    private Integer missedDays;
    private BigDecimal eminAmount;
    private BigDecimal fineAmount;
    private BigDecimal totalDue;
    private String status;
}
