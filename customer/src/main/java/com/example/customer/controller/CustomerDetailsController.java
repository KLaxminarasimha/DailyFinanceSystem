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
    @PostMapping("/{id}/employee")
    public EmployeeDetails addEmployee(@PathVariable Long id,
                                       @RequestBody EmployeeDTO dto) {
        return employeeService.addEmployee(id, dto);
    }

    // ✅ Business API
    @PostMapping("/{id}/business")
    public BusinessDetails addBusiness(@PathVariable Long id,
                                       @RequestBody BusinessDTO dto) {
        return businessService.addBusiness(id, dto);
    }
}