package com.example.customer.serviceImpl;

import com.example.customer.common.constants.AppConstants;
import com.example.customer.common.validation.ValidationUtil;
import com.example.customer.dto.EmployeeDetailsRequest;
import com.example.customer.dto.EmployeeDetailsResponse;
import com.example.customer.entity.Customer;
import com.example.customer.entity.EmployeeDetails;
import com.example.customer.exception.ResourceNotFoundException;
import com.example.customer.mapper.EmployeeDetailsMapper;
import com.example.customer.repository.EmployeeDetailsRepository;
import com.example.customer.service.EmployeeDetailsService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeDetailsServiceImpl implements EmployeeDetailsService {

    private final EmployeeDetailsRepository employeeRepository;
    private final ValidationUtil validationUtil;

    // ✅ ADD EMPLOYEE DETAILS
    @Override
    public EmployeeDetailsResponse addEmployeeDetails(Long customerId, EmployeeDetailsRequest request) {

        // 🔥 STEP 1: Get customer
        Customer customer = validationUtil.getCustomerOrThrow(customerId);

        // 🔥 STEP 2: Validate
        validationUtil.validateEmployee(customer, customerId);

        // STEP 3: Save
        EmployeeDetails emp = EmployeeDetailsMapper.toEntity(request);
        emp.setCustomer(customer);

        employeeRepository.save(emp);

        return EmployeeDetailsMapper.toResponse(emp);
    }

    // ✅ GET EMPLOYEE DETAILS
    @Override
    public EmployeeDetailsResponse getEmployeeDetails(Long customerId) {

        EmployeeDetails emp = employeeRepository.findByCustomerCustomerId(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(AppConstants.EMPLOYEE_NOT_FOUND)
                );

        return EmployeeDetailsMapper.toResponse(emp);
    }
}