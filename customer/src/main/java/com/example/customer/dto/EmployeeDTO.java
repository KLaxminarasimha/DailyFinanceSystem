package com.example.customer.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class EmployeeDTO {
    private String empId;
    private String companyName;
    private BigDecimal ctc;
    private BigDecimal monthlySalary;
    private Integer experience;
}