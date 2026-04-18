package com.example.customer.controller;

import com.example.customer.common.ApiResponse;
import com.example.customer.common.constants.AppConstants;
import com.example.customer.dto.CreateGuarantorRequest;
import com.example.customer.dto.GuarantorResponse;
import com.example.customer.service.GuarantorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class GuarantorController {

    private final GuarantorService guarantorService;

    @PostMapping("/{customerId}/guarantors")
    public ResponseEntity<ApiResponse<GuarantorResponse>> addGuarantor(
            @PathVariable Long customerId,
            @Valid @RequestBody CreateGuarantorRequest request) {

        return ResponseEntity.status(201).body(
                ApiResponse.success(
                        guarantorService.addGuarantor(customerId, request),
                        AppConstants.GUARANTOR_CREATED,
                        201
                )
        );
    }

    @GetMapping("/{customerId}/guarantors")
    public ResponseEntity<ApiResponse<List<GuarantorResponse>>> getGuarantors(
            @PathVariable Long customerId) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        guarantorService.getGuarantorsByCustomer(customerId),
                        AppConstants.GUARANTOR_FETCHED,
                        200
                )
        );
    }
}