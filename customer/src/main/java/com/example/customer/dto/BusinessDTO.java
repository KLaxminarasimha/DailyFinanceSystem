package com.example.customer.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BusinessDTO {
    private String businessName;
    private String businessType;
    private String gstNumber;
    private BigDecimal monthlyIncome;
}