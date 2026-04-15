package com.uniquehire.plansmodule.dto.request;

import com.uniquehire.plansmodule.enums.PlanStatus;
import com.uniquehire.plansmodule.enums.PlanType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePlanRequest {

    @NotNull(message = "Plan name is required")
    private PlanType name;

    @NotNull(message = "Total amount is required")
    @DecimalMin(value = "0.01", message = "Total amount must be greater than 0")
    private BigDecimal totalAmount;

    @NotNull(message = "Advance is required")
    @DecimalMin(value = "0.00", message = "Advance must be greater than or equal to 0")
    private BigDecimal advance;

    @NotNull(message = "Daily EMI is required")
    @DecimalMin(value = "0.01", message = "Daily EMI must be greater than 0")
    private BigDecimal dailyEmi;

    @NotNull(message = "Days is required")
    @Min(value = 1, message = "Days must be at least 1")
    private Integer days;

    @NotNull(message = "Status is required")
    private PlanStatus status;
}