package com.uniquehire.paymentservice.controller;

import com.uniquehire.paymentservice.dtos.Request.OtpVerifyRequest;
import com.uniquehire.paymentservice.dtos.Request.PayDueRequest;
import com.uniquehire.paymentservice.dtos.Request.PaymentRequest;
import com.uniquehire.paymentservice.dtos.Response.ApiResponse;
import com.uniquehire.paymentservice.dtos.Response.PaymentResponse;
import com.uniquehire.paymentservice.service.PaymentService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.uniquehire.paymentservice.constants.MessageConstants.*;

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
                PAYMENT_SUCCESS,
                response,
//                service.payEmi(loanId,req),
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
                OTP_SENT,
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
                OTP_VERIFIED,
                response,
//                service.verifyOtp(req),
                200
        );
    }

    //  Pay Due
    @PutMapping("/pay-due")
    public ApiResponse<PaymentResponse> payDue(
            @RequestBody PayDueRequest req) {

        PaymentResponse response = service.payDue(req);

        return ApiResponse.success(
                DUE_PAYMENT_SUCCESS,
//                service.payDue(req),
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
                PAYMENT_LIST,
                payments,
//                service.getPayments(loanId),
                200
        );
    }
}