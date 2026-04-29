package com.example.customer.service;

import com.example.customer.dto.EmployeeDTO;
import com.example.customer.entity.EmployeeDetails;

public interface EmployeeDetailsService {

    public EmployeeDetails addEmployeeByUserId(Long userId, EmployeeDTO dto);
}