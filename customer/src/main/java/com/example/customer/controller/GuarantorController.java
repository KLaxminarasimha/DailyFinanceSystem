package com.example.customer.controller;

import com.example.customer.common.ApiResponse;
import com.example.customer.dto.CreateGuarantorRequest;
import com.example.customer.dto.GuarantorResponse;
import com.example.customer.service.GuarantorService;
import com.example.customer.common.constants.AppConstants;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers") // 🔥 versioning
@RequiredArgsConstructor
public class GuarantorController {

    private final GuarantorService guarantorService;

    // ADD GUARANTOR TO CUSTOMER
    @PostMapping("/{customerId}/guarantors")
    public ApiResponse<GuarantorResponse> addGuarantor(
            @PathVariable Long customerId,
            @Valid @RequestBody CreateGuarantorRequest request) {

        GuarantorResponse response = guarantorService.addGuarantor(customerId, request);

        return ApiResponse.success(
                response,
                AppConstants.GUARANTOR_CREATED,
                201
        );
    }

    //  GET GUARANTORS BY CUSTOMER
    @GetMapping("/{customerId}/guarantors")
    public ApiResponse<List<GuarantorResponse>> getGuarantors(
            @PathVariable Long customerId) {

        List<GuarantorResponse> response =
                guarantorService.getGuarantorsByCustomer(customerId);

        return ApiResponse.success(
                response,
                AppConstants.GUARANTOR_FETCHED,
                200
        );
    }
}