package com.uniquehire.plansmodule.dto;


import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanResponse {
    private Long planId;
    private BigDecimal planAmount;
    private BigDecimal disbursedAmount;
    private BigDecimal interestAmount;
    private BigDecimal totalPayable;
    private BigDecimal dailyEmi;
    private Integer duration;
    private String status;
}