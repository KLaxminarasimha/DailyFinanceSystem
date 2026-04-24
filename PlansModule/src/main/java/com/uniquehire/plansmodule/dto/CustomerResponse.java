package com.uniquehire.plansmodule.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CustomerResponse {

    private Long id;
    private String userType;

    private EmployeeDTO employeeDetails;
    private BusinessDTO businessDetails;
}