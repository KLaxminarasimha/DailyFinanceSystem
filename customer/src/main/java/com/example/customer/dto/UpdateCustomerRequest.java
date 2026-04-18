package com.example.customer.dto;

import com.example.customer.enums.Gender;
import com.example.customer.enums.UserType; // ✅ ADD
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateCustomerRequest {

    private String firstName;
    private String lastName;

    @Email(message = "Invalid email format")
    private String email;

    private String address;

    private String pincode;

    private LocalDate dob;

    private Gender gender;

    // 🔥 ADD THIS (FIXES ERROR)
    private UserType userType;
}