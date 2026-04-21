package com.example.customer.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class KycVerificationRequest {

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotBlank(message = "Aadhar is required")
    @Pattern(regexp = "\\d{12}", message = "Aadhar must be 12 digits")
    private String aadhar;

    @NotBlank(message = "PAN is required")
    @Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]{1}", message = "Invalid PAN format")
    private String pan;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "\\d{10}", message = "Phone must be 10 digits")
    private String phone;
}