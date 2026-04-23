package com.example.customer.controller;

import com.example.customer.common.ApiResponse;
import com.example.customer.common.constants.AppConstants;
import com.example.customer.dto.KycResponse;
import com.example.customer.dto.KycStatusUpdateRequest;
import com.example.customer.dto.KycVerificationRequest;
import com.example.customer.service.KycService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kyc")
@RequiredArgsConstructor
public class KycController {

    private final KycService kycService;

    // ✅ SUBMIT KYC
    @PostMapping("/{customerId}")
    public ResponseEntity<ApiResponse<KycResponse>> submitKyc(
            @PathVariable Long customerId,
            @Valid @RequestBody KycVerificationRequest request) {

        KycResponse response = kycService.submitKyc(customerId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.success(
                        response,
                        AppConstants.KYC_SUBMITTED,
                        HttpStatus.CREATED.value()
                )
        );
    }

    // ✅ VERIFY / REJECT KYC
    @PutMapping("/{customerId}/status")
    public ResponseEntity<ApiResponse<KycResponse>> updateKycStatus(
            @PathVariable Long customerId,
            @Valid @RequestBody KycStatusUpdateRequest request) {

        KycResponse response = kycService.updateKycStatus(customerId, request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        response,
                        AppConstants.KYC_UPDATED,
                        HttpStatus.OK.value()
                )
        );
    }
}