package com.uniquehire.plansmodule.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SelectPlanRequest {
    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotNull(message = "Plan ID is required")
    private Long planId;
}
