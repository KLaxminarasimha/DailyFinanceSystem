package com.example.customer.controller;

import com.example.customer.dto.BusinessDTO;
import com.example.customer.dto.EmployeeDTO;
import com.example.customer.entity.BusinessDetails;
import com.example.customer.entity.EmployeeDetails;
import com.example.customer.service.BusinessDetailsService;
import com.example.customer.service.EmployeeDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerDetailsController {

    private final EmployeeDetailsService employeeService;
    private final BusinessDetailsService businessService;

    // ✅ Employee API
    @PostMapping("/employee")
    public EmployeeDetails addEmployee(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestBody EmployeeDTO dto) {

        return employeeService.addEmployeeByUserId(userId, dto);
    }


    // ✅ Business API
    @PostMapping("/business")
    public BusinessDetails addBusiness(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestBody BusinessDTO dto
    ) {
        return businessService.addBusinessByUserId(userId, dto);
    }
}