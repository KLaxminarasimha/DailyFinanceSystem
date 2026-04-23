package com.uniquehire.paymentservice.controller;

import com.uniquehire.paymentservice.constants.MessageConstants;
import com.uniquehire.paymentservice.dtos.Request.CreatePaymentRequest;
import com.uniquehire.paymentservice.dtos.Request.PayDueRequest;
import com.uniquehire.paymentservice.dtos.Request.PaymentRequest;
import com.uniquehire.paymentservice.dtos.Request.UpdatePaymentStatusRequest;
import com.uniquehire.paymentservice.dtos.Response.ApiResponse;
import com.uniquehire.paymentservice.dtos.Response.PaymentResponse;
import com.uniquehire.paymentservice.repository.PaymentRepository;
import com.uniquehire.paymentservice.service.PaymentService;
import com.uniquehire.paymentservice.utils.ResponseUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService paymentService;

    private final PaymentRepository paymentRepository;

    public PaymentController(PaymentService paymentService,
                             PaymentRepository paymentRepository) {
        this.paymentService = paymentService;
        this.paymentRepository = paymentRepository;
    }
    //Pay Emi
    @PostMapping("/pay")
    public PaymentResponse payEmi(@RequestBody PaymentRequest request) {
        return paymentService.payEmi(request);
    }


}