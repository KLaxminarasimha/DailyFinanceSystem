package com.example.customer.dto;

import jakarta.validation.constraints.*;
        import lombok.Data;

@Data
public class BusinessDetailsRequest {

    @NotBlank(message = "Business name is required")
    private String businessName;

    @NotBlank(message = "Business type is required")
    private String businessType;

    @NotBlank(message = "GST number is required")
    private String gstNumber;

    @NotNull(message = "Monthly income is required")
    @Positive(message = "Income must be positive")
    private Double monthlyIncome;

    @NotNull(message = "Years in business is required")
    @PositiveOrZero(message = "Years cannot be negative")
    private Integer yearsInBusiness;
}