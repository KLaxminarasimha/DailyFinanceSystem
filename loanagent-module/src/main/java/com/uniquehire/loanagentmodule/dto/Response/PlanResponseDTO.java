package com.uniquehire.loanagentmodule.dto.Response;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class PlanResponseDTO {

    private Long planId;
    private BigDecimal planAmount;
    private BigDecimal dailyEmi;
    private Integer duration;
}