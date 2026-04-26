package com.uniquehire.paymentservice.controller;

import com.uniquehire.paymentservice.dtos.Request.PaymentRequestDTO;
import com.uniquehire.paymentservice.dtos.Response.PaymentResponseDTO;
import com.uniquehire.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    // 🔥 PAY EMI (SECURE)
    @PostMapping("/pay")
    public PaymentResponseDTO pay(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestBody PaymentRequestDTO request) {

        return paymentService.makePayment(userId, request);
    }

    // 🔍 GET PAYMENTS
    @GetMapping("/{loanId}")
    public List<PaymentResponseDTO> getPayments(@PathVariable Long loanId) {
        return paymentService.getPayments(loanId);
    }
}