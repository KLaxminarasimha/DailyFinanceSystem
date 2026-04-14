package com.uniquehire.paymentservice.controller;

import com.uniquehire.paymentservice.constants.MessageConstants;
import com.uniquehire.paymentservice.dtos.Request.CreatePaymentRequest;
import com.uniquehire.paymentservice.dtos.Request.UpdatePaymentStatusRequest;
import com.uniquehire.paymentservice.dtos.Response.ApiResponse;
import com.uniquehire.paymentservice.dtos.Response.PaymentResponse;
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

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PaymentResponse>> recordPayment(@Valid @RequestBody CreatePaymentRequest request) {
        PaymentResponse response = paymentService.recordPayment(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseUtil.success(
                        MessageConstants.PAYMENT_RECORDED_SUCCESS,
                        response,
                        HttpStatus.CREATED.value()
                ));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getAllPayments() {
        List<PaymentResponse> response = paymentService.getAllPayments();
        return ResponseEntity.ok(
                ResponseUtil.success(
//                        "All payments fetched successfully",
                        MessageConstants.ALL_PAYMENTS_FETCHED_SUCCESS,
                        response,
                        HttpStatus.OK.value()
                )
        );
    }


    @GetMapping("/{loanId}")
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getPaymentsByLoan(@PathVariable Long loanId) {
        List<PaymentResponse> response = paymentService.getPaymentsByLoan(loanId);
        return ResponseEntity.ok(
                ResponseUtil.success(
                        MessageConstants.PAYMENTS_FETCHED_SUCCESS,
                        response,
                        HttpStatus.OK.value()
                )
        );
    }

    @PutMapping("/{paymentId}/status")
    public ResponseEntity<ApiResponse<PaymentResponse>> updatePaymentStatus(@PathVariable Long paymentId,
                                                                            @Valid @RequestBody UpdatePaymentStatusRequest request) {
        PaymentResponse response = paymentService.updatePaymentStatus(paymentId, request);
        return ResponseEntity.ok(
                ResponseUtil.success(
                        MessageConstants.PAYMENT_STATUS_UPDATED_SUCCESS,
                        response,
                        HttpStatus.OK.value()
                )
        );
    }

        @DeleteMapping("/{paymentId}")
        public ResponseEntity<ApiResponse<Object>> deletePayment(@PathVariable Long paymentId){
            paymentService.deletePayment(paymentId);
            return ResponseEntity.ok(
                    ResponseUtil.success(
//                            "Payment deleted successfully",
                            MessageConstants.PAYMENT_DELETED_SUCCESS,
                            null,
                            HttpStatus.OK.value()
                    )
            );
        }
}