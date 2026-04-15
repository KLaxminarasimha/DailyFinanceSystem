package com.example.customer.controller;

import com.example.customer.common.ApiResponse;
import com.example.customer.dto.CreateCustomerRequest;
import com.example.customer.dto.CustomerResponse;
import com.example.customer.dto.UpdateCustomerRequest;
import com.example.customer.service.CustomerService;
import com.example.customer.common.constants.AppConstants;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    //  CREATE CUSTOMER (UPDATED FOR FILE UPLOAD)
    @PostMapping(consumes = "multipart/form-data")
    public ApiResponse<CustomerResponse> createCustomer(
            @Valid @ModelAttribute CreateCustomerRequest request) {

        CustomerResponse response = customerService.createCustomer(request);

        return ApiResponse.success(
                response,
                AppConstants.CUSTOMER_CREATED,
                201
        );
    }

    //  GET ALL CUSTOMERS
    @GetMapping
    public ApiResponse<Page<CustomerResponse>> getAllCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<CustomerResponse> response = customerService.getAllCustomers(page, size);

        return ApiResponse.success(
                response,
                AppConstants.CUSTOMER_FETCHED,
                200
        );
    }

    //  GET CUSTOMER BY ID
    @GetMapping("/{id}")
    public ApiResponse<CustomerResponse> getCustomerById(@PathVariable Long id) {

        CustomerResponse response = customerService.getCustomerById(id);

        return ApiResponse.success(
                response,
                AppConstants.CUSTOMER_FETCHED,
                200
        );
    }

    //  UPDATE CUSTOMER
    @PutMapping("/{id}")
    public ApiResponse<CustomerResponse> updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCustomerRequest request) {

        CustomerResponse response = customerService.updateCustomer(id, request);

        return ApiResponse.success(
                response,
                AppConstants.CUSTOMER_UPDATED,
                200
        );
    }

    //  DELETE CUSTOMER
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteCustomer(@PathVariable Long id) {

        customerService.deleteCustomer(id);

        return ApiResponse.success(
                null,
                AppConstants.CUSTOMER_DELETED,
                200
        );
    }
}