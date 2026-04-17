package com.example.customer.dto;

import com.example.customer.enums.KycStatus;
import lombok.Data;

@Data
public class KycResponse {

    private Long id;
    private String aadhar;
    private String pan;
    private String phone;
    private KycStatus status;
    private String rejectionReason;
    private Long customerId;
}