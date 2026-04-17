package com.example.customer.controller;

import com.example.customer.common.ApiResponse;
import com.example.customer.common.constants.AppConstants;
import com.example.customer.dto.CreateCustomerRequest;
import com.example.customer.dto.CustomerResponse;
import com.example.customer.dto.UpdateCustomerRequest;
import com.example.customer.service.CustomerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    // ✅ CREATE CUSTOMER
    @PostMapping
    public ResponseEntity<ApiResponse<CustomerResponse>> createCustomer(
            @Valid @RequestBody CreateCustomerRequest request) {

        CustomerResponse response = customerService.createCustomer(request);

        return ResponseEntity.ok(
                ApiResponse.success(response, AppConstants.CUSTOMER_CREATED, 200)
        );
    }

    // ✅ GET ALL CUSTOMERS
    @GetMapping
    public ResponseEntity<ApiResponse<Page<CustomerResponse>>> getAllCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<CustomerResponse> response = customerService.getAllCustomers(page, size);

        return ResponseEntity.ok(
                ApiResponse.success(response, AppConstants.CUSTOMER_FETCHED, 200)
        );
    }

    // ✅ GET CUSTOMER BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerResponse>> getCustomerById(
            @PathVariable Long id) {

        CustomerResponse response = customerService.getCustomerById(id);

        return ResponseEntity.ok(
                ApiResponse.success(response, AppConstants.CUSTOMER_FETCHED, 200)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerResponse>> updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCustomerRequest request) {

        CustomerResponse response = customerService.updateCustomer(id, request);

        return ResponseEntity.ok(
                ApiResponse.success(response, AppConstants.CUSTOMER_UPDATED, 200)
        );
    }

    // ✅ DELETE CUSTOMER
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCustomer(
            @PathVariable Long id) {

        customerService.deleteCustomer(id);

        return ResponseEntity.ok(
                ApiResponse.success(null, AppConstants.CUSTOMER_DELETED, 200)
        );
    }
}