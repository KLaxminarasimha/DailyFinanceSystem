
package com.example.customer.controller;

import com.example.customer.common.ApiResponse;
import com.example.customer.common.constants.AppConstants;
import com.example.customer.dto.KycResponse;
import com.example.customer.dto.KycStatusUpdateRequest;
import com.example.customer.dto.KycVerificationRequest;
import com.example.customer.service.KycService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

        return ResponseEntity.ok(
                ApiResponse.success(
                        kycService.submitKyc(customerId, request),
                        AppConstants.KYC_SUBMITTED,   // ✅ FIX
                        200
                )
        );
    }

    // ✅ VERIFY / REJECT
    @PutMapping("/{customerId}/status")
    public ResponseEntity<ApiResponse<KycResponse>> updateKycStatus(
            @PathVariable Long customerId,
            @Valid @RequestBody KycStatusUpdateRequest request) { // ✅ ADD @Valid

        return ResponseEntity.ok(
                ApiResponse.success(
                        kycService.updateKycStatus(customerId, request),
                        AppConstants.KYC_UPDATED,     // ✅ FIX
                        200
                )
        );
    }
}