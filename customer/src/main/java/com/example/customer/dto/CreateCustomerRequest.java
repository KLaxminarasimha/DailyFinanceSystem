package com.example.customer.dto;

import com.example.customer.enums.Gender;
import com.example.customer.enums.UserType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateCustomerRequest {

        @NotBlank(message = "First name is required")
        @Size(max = 50)
        private String firstName;

        @NotBlank(message = "Last name is required")
        @Size(max = 50)
        private String lastName;

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        private String email;

        @NotBlank(message = "Address is required")
        private String address;

        @NotBlank(message = "Pincode is required")
        @Pattern(regexp = "\\d{6}", message = "Pincode must be 6 digits")
        private String pincode;

        @NotBlank(message = "PAN is required")
        @Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]{1}", message = "Invalid PAN format")
        private String panNumber;

        @NotNull(message = "DOB is required")
        private LocalDate dob;

        @NotNull(message = "Gender is required")
        private Gender gender;

        @NotNull(message = "User type is required")
        private UserType userType;
}