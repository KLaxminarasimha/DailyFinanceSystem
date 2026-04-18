package com.example.customer.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class GuarantorResponse {

    private Long guarantorId;
    private String name;
    private String phone;
    private String email;
    private String relationship;
    private Boolean verified;
    private Long customerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}