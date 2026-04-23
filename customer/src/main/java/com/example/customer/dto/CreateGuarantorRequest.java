package com.example.customer.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateGuarantorRequest {

    @NotBlank(message = "Name is required")
    @Size(max = 100)
    private String name;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "\\d{10}", message = "Phone must be 10 digits")
    private String phone;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "PAN is required")
    @Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]{1}", message = "Invalid PAN format")
    private String panNumber;

    @NotBlank(message = "Relationship is required")
    @Size(max = 50)
    private String relationship;
}