package com.example.customer.controller;

import com.example.customer.common.ApiResponse;
import com.example.customer.common.constants.AppConstants;
import com.example.customer.dto.BusinessDetailsRequest;
import com.example.customer.dto.BusinessDetailsResponse;
import com.example.customer.service.BusinessDetailsService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/business")
@RequiredArgsConstructor
public class BusinessDetailsController {

    private final BusinessDetailsService service;

    // ✅ ADD BUSINESS
    @PostMapping("/{customerId}")
    public ResponseEntity<ApiResponse<BusinessDetailsResponse>> addBusinessDetails(
            @PathVariable Long customerId,
            @Valid @RequestBody BusinessDetailsRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        service.addBusinessDetails(customerId, request),
                        AppConstants.BUSINESS_CREATED,   // ✅ USED
                        200
                )
        );
    }

    // ✅ GET BUSINESS
    @GetMapping("/{customerId}")
    public ResponseEntity<ApiResponse<BusinessDetailsResponse>> getBusinessDetails(
            @PathVariable Long customerId) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        service.getBusinessDetails(customerId),
                        AppConstants.BUSINESS_FETCHED,   // ✅ USED
                        200
                )
        );
    }
}