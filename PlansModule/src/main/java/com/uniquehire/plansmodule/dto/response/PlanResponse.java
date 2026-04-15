package com.uniquehire.plansmodule.dto.response;


import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanResponse {
    private Long planId;
    private String name;
    private BigDecimal totalAmount;
    private BigDecimal advance;
    private BigDecimal dailyEmi;
    private Integer days;
    private String status;
    private LocalDateTime createdAt;
}