package com.example.customer.dto;

import lombok.Data;

@Data
public class EmployeeDTO {
    private String empId;
    private String companyName;
    private Double ctc;
    private Double monthlySalary;
    private Integer experience;
}