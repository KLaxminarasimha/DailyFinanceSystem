package com.example.customer.dto;

import lombok.Data;

@Data
public class BusinessDTO {
    private String businessName;
    private String businessType;
    private String gstNumber;
    private Double monthlyIncome;
}