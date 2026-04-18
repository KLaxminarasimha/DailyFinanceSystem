
package com.example.customer.dto;

import lombok.Data;

@Data
public class EmployeeDetailsResponse {

    private Long id;
    private String companyName;
    private String employeeId;
    private Double monthlyIncome;
    private Integer experience;
    private Long customerId;
}