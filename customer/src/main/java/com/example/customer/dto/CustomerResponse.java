package com.example.customer.dto;

import com.example.customer.enums.KycStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class CustomerResponse {
    private Long customerId;
    private String name;
    private String phone;
    private String email;
    private String address;
    private Double income;
    private KycStatus kycStatus;

    private List<GuarantorResponse> guarantors;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
