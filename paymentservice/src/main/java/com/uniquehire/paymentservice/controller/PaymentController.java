package com.uniquehire.paymentservice.controller;

import com.uniquehire.paymentservice.dtos.Request.OtpVerifyRequest;
import com.uniquehire.paymentservice.dtos.Request.PayDueRequest;
import com.uniquehire.paymentservice.dtos.Request.PaymentRequest;
import com.uniquehire.paymentservice.dtos.Response.ApiResponse;
import com.uniquehire.paymentservice.dtos.Response.PaymentResponse;
import com.uniquehire.paymentservice.service.PaymentService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private PaymentService service;

    // constructor instead of @RequiredArgsConstructor
    public PaymentController(PaymentService service) {
        this.service = service;
    }

    //  Pay EMI
    @PostMapping("/{loanId}")
    public ApiResponse<PaymentResponse> pay(
            @PathVariable Long loanId,
            @RequestBody PaymentRequest req) {

        PaymentResponse response = service.payEmi(loanId, req);

        return ApiResponse.success(
                "Payment successful",
                response,
                200
        );
    }

    // Send OTP
    @PostMapping("/send-otp/{paymentId}")
    public ApiResponse<String> sendOtp(
            @PathVariable Long paymentId,
            @RequestParam String email) {

        String message = service.sendOtp(paymentId, email);

        return ApiResponse.success(
                message,
                null,
                200
        );
    }

    // Verify OTP
    @PostMapping("/verify-otp")
    public ApiResponse<PaymentResponse> verify(
            @RequestBody OtpVerifyRequest req) {

        PaymentResponse response = service.verifyOtp(req);

        return ApiResponse.success(
                "OTP verified successfully",
                response,
                200
        );
    }

    //  Pay Due
    @PutMapping("/pay-due")
    public ApiResponse<PaymentResponse> payDue(
            @RequestBody PayDueRequest req) {

        PaymentResponse response = service.payDue(req);

        return ApiResponse.success(
                "Due payment successful",
                response,
                200
        );
    }

    //  Get Payments
    @GetMapping("/{loanId}")
    public ApiResponse<List<PaymentResponse>> get(
            @PathVariable Long loanId) {

        List<PaymentResponse> payments = service.getPayments(loanId);

        return ApiResponse.success(
                "Payments retrieved successfully",
                payments,
                200
        );
    }
}