
package com.uniquehire.paymentservice.service.impl;

import com.uniquehire.paymentservice.dtos.Request.PayDueRequest;
import com.uniquehire.paymentservice.dtos.Request.PaymentRequest;
import com.uniquehire.paymentservice.dtos.Response.PaymentResponse;
import com.uniquehire.paymentservice.entity.Fine;
import com.uniquehire.paymentservice.entity.Payment;
import com.uniquehire.paymentservice.enums.FineStatus;
import com.uniquehire.paymentservice.enums.PaymentMethod;
import com.uniquehire.paymentservice.enums.PaymentStatus;
import com.uniquehire.paymentservice.exception.BusinessException;
import com.uniquehire.paymentservice.exception.ResourceNotFoundException;
import com.uniquehire.paymentservice.repository.FineRepository;
import com.uniquehire.paymentservice.repository.PaymentRepository;
import com.uniquehire.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService{

    private final PaymentRepository paymentRepository;
    private final FineRepository fineRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              FineRepository fineRepository) {
        this.paymentRepository = paymentRepository;
        this.fineRepository = fineRepository;
    }
    @Override
    public PaymentResponse payEmi(PaymentRequest request) {

        // 👉 Assume we get from Loan Module
        BigDecimal totalLoanAmount = new BigDecimal("10000");

        // Step 1: EMI = 1%
        BigDecimal emi = totalLoanAmount.multiply(new BigDecimal("0.01"));

        // Step 2: Fine = 1% of EMI
        BigDecimal fine = emi.multiply(new BigDecimal("0.01"));

        BigDecimal paidAmount = request.getPayAmount();

        BigDecimal due = BigDecimal.ZERO;
        int daysCovered = 0;

        LocalDate today = LocalDate.now();
        LocalDate nextDate;

        PaymentStatus status;

        // 🔴 CASE 1: Partial Payment
        if (paidAmount.compareTo(emi) < 0) {

            due = emi.subtract(paidAmount);

            nextDate = today.plusDays(1);

            status = PaymentStatus.PENDING;

            // save fine
            saveFine(request.getLoanId(), fine, "Partial payment");

        }

        // 🟢 CASE 2: Exact Payment
        else if (paidAmount.compareTo(emi) == 0) {

            nextDate = today.plusDays(1);

            status = PaymentStatus.PAID;
        }

        // 🔵 CASE 3: Extra Payment
        else {

            daysCovered = paidAmount.divide(emi).intValue();

            BigDecimal remaining = paidAmount.remainder(emi);

            nextDate = today.plusDays(daysCovered);

            status = PaymentStatus.PAID;

            // optional: remaining can be stored as advance
            if (remaining.compareTo(BigDecimal.ZERO) > 0) {
                System.out.println("Advance amount: " + remaining);
            }
        }

        // 👉 Save Payment
        Payment payment = new Payment();
        payment.setLoanId(request.getLoanId());
        payment.setPaymentDate(today);
        payment.setEmiAmount(emi);
        payment.setPaidAmount(paidAmount);
        payment.setDueAmount(due);
        payment.setFineAmount(fine);
        payment.setDaysCovered(daysCovered);
        payment.setNextEmiDate(nextDate);
        payment.setStatus(status);
        payment.setPaymentMethod(PaymentMethod.valueOf(request.getPaymentMethod()));
        payment.setUpiId(request.getUpiId());

        Payment saved = paymentRepository.save(payment);

        return mapToResponse(saved);
    }

    // ✅ PAY REMAINING DUE
    @Override
    public PaymentResponse payDue(PayDueRequest request) {

        Payment payment = paymentRepository.findById(request.getPaymentId())
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        BigDecimal due = payment.getDueAmount();
        BigDecimal paid = request.getAmountPaid();

        // If paid full due
        if (paid.compareTo(due) >= 0) {

            payment.setDueAmount(BigDecimal.ZERO);
            payment.setStatus(PaymentStatus.PAID);

        } else {

            // still pending
            BigDecimal remaining = due.subtract(paid);
            payment.setDueAmount(remaining);
            payment.setStatus(PaymentStatus.PENDING);
        }

        Payment updated = paymentRepository.save(payment);

        return mapToResponse(updated);
    }

    // ✅ GET PAYMENTS
    @Override
    public List<PaymentResponse> getPaymentsByLoanId(Long loanId) {

        return paymentRepository.findByLoanId(loanId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // 🔧 HELPER: SAVE FINE
    private void saveFine(Long loanId, BigDecimal amount, String reason) {

        Fine fine = new Fine();
        fine.setLoanId(loanId);
        fine.setFineAmount(amount);
        fine.setReason(reason);
        fine.setDate(LocalDate.now());
        fine.setStatus(FineStatus.PENDING);

        fineRepository.save(fine);
    }

    // 🔧 HELPER: MAP ENTITY → DTO
    private PaymentResponse mapToResponse(Payment payment) {

        PaymentResponse res = new PaymentResponse();

        res.setPaymentId(payment.getPaymentId());
        res.setLoanId(payment.getLoanId());
        res.setEmiAmount(payment.getEmiAmount());
        res.setPaidAmount(payment.getPaidAmount());
        res.setDueAmount(payment.getDueAmount());
        res.setFineAmount(payment.getFineAmount());
        res.setDaysCovered(payment.getDaysCovered());
        res.setNextEmiDate(payment.getNextEmiDate());
        res.setStatus(payment.getStatus().name());

        return res;
    }
}