package com.uniquehire.loanagentmodule.dto.Response;

import com.uniquehire.loanagentmodule.enums.LoanStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class LoanResponseDTO {

    private Long loanId;
    private Long customerId;
    private Long planId;
    private Long agentId;
    private BigDecimal totalAmount;
    private BigDecimal advance;
    private BigDecimal givenAmount;
    private BigDecimal dailyEmi;
    private Integer days;
    private LocalDate startDate;
    private LocalDate endDate;
    private LoanStatus status;
    private Integer overdueDays;
    private BigDecimal totalFine;

}
