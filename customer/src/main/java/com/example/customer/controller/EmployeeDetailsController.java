package com.example.customer.controller;

import com.example.customer.common.ApiResponse;
import com.example.customer.common.constants.AppConstants;
import com.example.customer.dto.EmployeeDetailsRequest;
import com.example.customer.dto.EmployeeDetailsResponse;
import com.example.customer.service.EmployeeDetailsService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeDetailsController {

    private final EmployeeDetailsService service;

    // ✅ ADD EMPLOYEE DETAILS
    @PostMapping("/{customerId}")
    public ResponseEntity<ApiResponse<EmployeeDetailsResponse>> addEmployeeDetails(
            @PathVariable Long customerId,
            @Valid @RequestBody EmployeeDetailsRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        service.addEmployeeDetails(customerId, request),
                        AppConstants.EMPLOYEE_CREATED,   // ✅ USED HERE
                        200
                )
        );
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<ApiResponse<EmployeeDetailsResponse>> getEmployeeDetails(
            @PathVariable Long customerId) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        service.getEmployeeDetails(customerId),
                        AppConstants.EMPLOYEE_FETCHED,   // ✅ USED HERE
                        200
                )
        );
    }
}