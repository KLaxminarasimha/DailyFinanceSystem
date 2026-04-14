package com.example.customer.dto;

import jakarta.validation.constraints.*;

import lombok.Data;

import java.util.List;

@Data
public class CreateCustomerRequest {
        @NotBlank(message = "Name is required")
        @Size(max = 100)
        private String name;

        @NotBlank(message = "Phone is required")
        @Pattern(regexp = "\\d{10}", message = "Phone must be 10 digits")
        private String phone;

        @Email(message = "Invalid email format")
        private String email;

        @Size(max = 500)
        private String address;

        @NotBlank(message = "Aadhar is required")
        @Pattern(regexp = "\\d{12}", message = "Aadhar must be 12 digits")
        private String aadhar;

        @NotBlank(message = "PAN is required")
        @Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]", message = "Invalid PAN format")
        private String pan;

        @NotNull(message = "Income is required")
        @Positive(message = "Income must be positive")
        private Double income;

        private List<CreateGuarantorRequest> guarantors;
}
