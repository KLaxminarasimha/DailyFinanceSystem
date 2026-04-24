package com.uniquehire.paymentservice.mapper;

import com.uniquehire.paymentservice.dtos.Response.PaymentResponse;
import com.uniquehire.paymentservice.entity.Payment;

public class PaymentMapper {

    public PaymentResponse map(Payment p) {
        PaymentResponse response = new PaymentResponse();

        response.setPaymentId(p.getPaymentId());
        response.setLoanId(p.getLoanId());
        response.setEmiAmount(p.getEmiAmount());
        response.setPaidAmount(p.getPaidAmount());
        response.setDueAmount(p.getDueAmount());
        response.setFineAmount(p.getFineAmount());
        response.setDaysCovered(p.getDaysCovered());
        response.setNextEmiDate(p.getNextEmiDate());
        response.setStatus(p.getStatus());

        return response;
    }
}