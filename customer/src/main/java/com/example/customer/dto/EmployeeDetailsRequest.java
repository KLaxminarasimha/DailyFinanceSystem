package com.example.customer.dto;

import jakarta.validation.constraints.*;
        import lombok.Data;

@Data
public class EmployeeDetailsRequest {

    @NotBlank(message = "Company name is required")
    private String companyName;

    @NotBlank(message = "Employee ID is required")
    private String employeeId;

    @NotNull(message = "Monthly salary is required")
    @Positive(message = "Salary must be positive")
    private Double monthlyIncome;

    @NotNull(message = "Experience is required")
    @PositiveOrZero(message = "Experience cannot be negative")
    private Integer experience;
}