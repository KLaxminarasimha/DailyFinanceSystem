package com.uniquehire.plansmodule.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EligibilityResponse {
    private Long customerId;
    private BigDecimal income;
    private List<PlanResponse> eligiblePlans;
}