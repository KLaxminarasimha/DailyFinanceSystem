package com.example.customer.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class KycStatusUpdateRequest {

    @NotBlank
    private String status; // VERIFIED or REJECTED

    private String rejectionReason;
}