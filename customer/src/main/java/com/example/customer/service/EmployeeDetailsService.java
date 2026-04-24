package com.example.customer.service;

import com.example.customer.dto.EmployeeDTO;
import com.example.customer.entity.EmployeeDetails;

public interface EmployeeDetailsService {

    EmployeeDetails addEmployee(Long customerId, EmployeeDTO dto);
}