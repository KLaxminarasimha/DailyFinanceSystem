package com.example.customer.dto;

import com.example.customer.enums.Gender;
import com.example.customer.enums.KycStatus;
import com.example.customer.enums.UserType;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CustomerResponse {

    private Long customerId;

    private String firstName;
    private String lastName;

    private String email;
    private String address;
    private String pincode;

    private LocalDate dob;
    private Gender gender;

    // 🔥 ADD THIS (VERY IMPORTANT)
    private UserType userType;

    // 🔥 KYC tracking
    private KycStatus kycStatus;

    // 🔥 audit fields
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<GuarantorResponse> guarantors;
}