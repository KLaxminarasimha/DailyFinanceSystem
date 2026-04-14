package com.uniquehire.paymentservice.service;

import com.uniquehire.paymentservice.dtos.Request.CreatePaymentRequest;
import com.uniquehire.paymentservice.dtos.Request.UpdatePaymentStatusRequest;
import com.uniquehire.paymentservice.dtos.Response.PaymentResponse;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentService {
    PaymentResponse recordPayment(CreatePaymentRequest request);

    List<PaymentResponse> getPaymentsByLoan(Long loanId);

    List<PaymentResponse> getAllPayments();

    PaymentResponse updatePaymentStatus(Long paymentId, UpdatePaymentStatusRequest request);

    void deletePayment(Long paymentId);

    void validatePaymentAmount(BigDecimal paidAmount, BigDecimal emiAmount);
}
