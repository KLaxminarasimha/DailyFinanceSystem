package com.example.customer.dto;

import jakarta.validation.constraints.*;

import lombok.Data;

@Data
public class UpdateCustomerRequest {

    @Size(max = 100, message = "Name cannot exceed 100 characters")
    private String name;

    @Email(message = "Invalid email format")
    private String email;

    @Positive(message = "Income must be positive")
    private Double income;

    @Size(max = 500, message = "Address cannot exceed 500 characters")
    private String address;
}
