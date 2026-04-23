package com.uniquehire.TransactionAndReport.dto.Extra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefaultWarningDto {
    private Long loanId;
    private Integer missedDays;
    private BigDecimal totalDue;
    private String riskMessage;
    private String status;
}
