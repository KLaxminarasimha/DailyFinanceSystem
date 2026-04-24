package com.example.customer.dto;

import lombok.Data;

@Data
public class KycDTO {
    private String aadhar;
    private String pan;
    private String email;
    private String phone;
    private String ifsc;
    private String accountNumber;
}