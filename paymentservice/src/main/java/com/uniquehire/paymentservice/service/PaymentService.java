package com.uniquehire.paymentservice.service;

import com.uniquehire.paymentservice.dtos.Request.PaymentRequestDTO;
import com.uniquehire.paymentservice.dtos.Response.PaymentResponseDTO;

import java.util.List;

public interface PaymentService {

   PaymentResponseDTO makePayment(Long userId, PaymentRequestDTO request);

   List<PaymentResponseDTO> getPayments(Long loanId);
}