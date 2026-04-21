package com.uniquehire.loanagentmodule.dto.Response;


import com.uniquehire.loanagentmodule.enums.LoanStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PlanResponseDTO {

    private Long planId;
    private String planName;
    private BigDecimal totalAmount;
    private BigDecimal advance;
    private BigDecimal givenAmount;
    private BigDecimal dailyEmi;
    private Integer days;

}