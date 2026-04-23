package com.uniquehire.paymentservice.service;

import com.uniquehire.paymentservice.dtos.Request.CreatePaymentRequest;
import com.uniquehire.paymentservice.dtos.Request.PayDueRequest;
import com.uniquehire.paymentservice.dtos.Request.PaymentRequest;
import com.uniquehire.paymentservice.dtos.Request.UpdatePaymentStatusRequest;
import com.uniquehire.paymentservice.dtos.Response.PaymentResponse;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentService {
   PaymentResponse payEmi(PaymentRequest request ); //emi payment

    PaymentResponse payDue(PayDueRequest request); //pay remaining due

    List<PaymentResponse> getPaymentsByLoanId(Long loanId);//get all payment for a loan

}
