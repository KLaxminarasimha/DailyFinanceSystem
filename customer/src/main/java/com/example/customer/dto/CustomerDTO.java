package com.example.customer.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate dob;
    private String gender;
    private String address;
    private String city;
    private String state;
    private String pincode;
    private String userType;
}
