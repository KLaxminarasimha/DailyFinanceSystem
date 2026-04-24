package com.uniquehire.paymentservice.service;

//import com.uniquehire.paymentservice.dtos.Request.CreatePaymentRequest;
import com.uniquehire.paymentservice.dtos.Request.CreateFineRequest;
import com.uniquehire.paymentservice.dtos.Request.OtpVerifyRequest;
import com.uniquehire.paymentservice.dtos.Request.PayDueRequest;
import com.uniquehire.paymentservice.dtos.Request.PaymentRequest;
//import com.uniquehire.paymentservice.dtos.Request.UpdatePaymentStatusRequest;
import com.uniquehire.paymentservice.dtos.Response.PaymentResponse;

//import java.math.BigDecimal;
import java.util.List;

public interface PaymentService {

   PaymentResponse payEmi(Long loanId, PaymentRequest request );

   String sendOtp(Long paymentId, String email);

   PaymentResponse verifyOtp(OtpVerifyRequest request);

   PaymentResponse payDue(PayDueRequest request);

   List<PaymentResponse> getPayments(Long loanId);
}

