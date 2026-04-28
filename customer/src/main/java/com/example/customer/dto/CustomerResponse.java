package com.example.customer.dto;

import lombok.Data;

@Data
public class CustomerResponse {

    private Long id;
    private String userType;
    private String firstName;
    private String lastName;

    private EmployeeDTO employeeDetails;
    private BusinessDTO businessDetails;
}