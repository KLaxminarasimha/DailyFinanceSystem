package com.uniquehire.TransactionAndReport.dto.Extra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MissedPaymentAlertDto {
    private Long loanId;
    private Integer missedDays;
    private BigDecimal emiAmount;
    private BigDecimal fineAmount;
    private BigDecimal totalDue;
    private String status;
}
