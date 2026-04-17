package com.example.customer.dto;

import lombok.Data;

@Data
public class BusinessDetailsResponse {

    private Long id;
    private String businessName;
    private String businessType;
    private String gstNumber;
    private Double monthlyIncome;
    private Integer yearsInBusiness;
    private Long customerId;
}