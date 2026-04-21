
package com.example.customer.service;

import com.example.customer.dto.EmployeeDetailsRequest;
import com.example.customer.dto.EmployeeDetailsResponse;

public interface EmployeeDetailsService {

    EmployeeDetailsResponse addEmployeeDetails(Long customerId, EmployeeDetailsRequest request);

    EmployeeDetailsResponse getEmployeeDetails(Long customerId);
}