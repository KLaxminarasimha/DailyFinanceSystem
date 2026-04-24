package com.uniquehire.paymentservice.service.impl;

import com.uniquehire.paymentservice.dtos.Request.*;
import com.uniquehire.paymentservice.dtos.Response.ApiResponse;
import com.uniquehire.paymentservice.dtos.Response.PaymentResponse;
import com.uniquehire.paymentservice.entity.Fine;
import com.uniquehire.paymentservice.entity.Payment;
import com.uniquehire.paymentservice.enums.FineStatus;
import com.uniquehire.paymentservice.enums.PaymentStatus;
import com.uniquehire.paymentservice.repository.FineRepository;
import com.uniquehire.paymentservice.repository.PaymentRepository;
import com.uniquehire.paymentservice.service.PaymentService;
import com.uniquehire.paymentservice.utils.OtpUtil;
import com.uniquehire.paymentservice.utils.PaymentCalculationUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final FineRepository fineRepository;
    private final OtpUtil otpUtil;
    private final RestTemplate restTemplate;

    @Value("${loan.service.base-url}")
    private String loanServiceUrl;

    private BigDecimal getLoanAmount(Long loanId) {

        String url = loanServiceUrl + "/" + loanId;

        ApiResponse response = restTemplate.getForObject(url, ApiResponse.class);

        if (response == null || !response.isSuccess()) {
            throw new RuntimeException("Loan not found");
        }

        Map<String, Object> data = (Map<String, Object>) response.getData();

        return new BigDecimal(data.get("totalAmount").toString());
    }

    // 🔹 Dummy loan data (replace with LoanService later)
//    private BigDecimal getLoanAmount(Long loanId) {
//        return BigDecimal.valueOf(10000);
//    }

    // ✅ PAY EMI
    @Override
    public PaymentResponse payEmi(Long loanId, PaymentRequest req) {

        BigDecimal loanAmount = getLoanAmount(loanId);
        BigDecimal emi = PaymentCalculationUtil.calculateEmi(loanAmount);
        BigDecimal paid = req.getPaidAmount();

        BigDecimal due = BigDecimal.ZERO;
        BigDecimal fine = BigDecimal.ZERO;
        int days = 0;

        // 🔥 EMI LOGIC
        if (paid.compareTo(emi) < 0) {
            due = emi.subtract(paid);
            fine = PaymentCalculationUtil.calculateFine(emi);
        } else if (paid.compareTo(emi) == 0) {
            days = 1;
        } else {
            days = PaymentCalculationUtil.calculateDays(paid, emi);

            BigDecimal remainder = paid.remainder(emi);
            if (remainder.compareTo(BigDecimal.ZERO) > 0) {
                due = emi.subtract(remainder);
                fine = PaymentCalculationUtil.calculateFine(emi);
            }
        }

        Payment payment = new Payment();
        payment.setLoanId(loanId);
        payment.setPaymentDate(req.getPaymentDate());
        payment.setEmiAmount(emi);
        payment.setPaidAmount(paid);
        payment.setDueAmount(due);
        payment.setFineAmount(fine);
        payment.setDaysCovered(days);
        payment.setNextEmiDate(LocalDate.now().plusDays(days));
        payment.setPaymentMethod(req.getPaymentMethod());
        payment.setUpiId(req.getUpiId());
        payment.setStatus(due.compareTo(BigDecimal.ZERO) > 0
                ? PaymentStatus.PENDING
                : PaymentStatus.COMPLETED);

        // ✅ Fine mapping
        if (fine.compareTo(BigDecimal.ZERO) > 0) {
            Fine f = new Fine();
            f.setLoanId(loanId);
            f.setFineAmount(fine);
            f.setReason("Late/Partial Payment");
            f.setDate(LocalDate.now());
            f.setStatus(FineStatus.PENDING);
            f.setPayment(payment);

            payment.getFines().add(f);
        }

        paymentRepository.save(payment);

        return mapToResponse(payment);
    }

    // 🔐 SEND OTP
    @Override
    public String sendOtp(Long paymentId, String email) {

        String otp = otpUtil.generateOtp(paymentId);

        // Simulated email
        System.out.println("OTP sent to " + email + " : " + otp);

        return "OTP sent successfully";
    }

    // 🔐 VERIFY OTP
    @Override
    public PaymentResponse verifyOtp(OtpVerifyRequest req) {

        Payment payment = paymentRepository.findById(req.getPaymentId())
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        boolean valid = otpUtil.verifyOtp(req.getPaymentId(), req.getOtp());

        if (!valid) {
            throw new RuntimeException("Invalid OTP");
        }

        payment.setStatus(PaymentStatus.COMPLETED);
        paymentRepository.save(payment);

        return mapToResponse(payment);
    }

    // 🔥 PAY DUE
    @Override
    public PaymentResponse payDue(PayDueRequest req) {

        List<Payment> pendingPayments =
                paymentRepository.findByLoanIdAndStatus(
                        req.getLoanId(), PaymentStatus.PENDING);

        BigDecimal totalDue = pendingPayments.stream()
                .map(Payment::getDueAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (req.getAmountPaid().compareTo(totalDue) < 0) {
            throw new RuntimeException("Please pay full due amount");
        }

        pendingPayments.forEach(p -> {
            p.setDueAmount(BigDecimal.ZERO);
            p.setStatus(PaymentStatus.COMPLETED);
        });

        paymentRepository.saveAll(pendingPayments);

        return mapToResponse(pendingPayments.get(0));
    }

    // 📄 GET PAYMENTS
    @Override
    public List<PaymentResponse> getPayments(Long loanId) {

        List<Payment> payments = paymentRepository.findByLoanId(loanId);

        return payments.stream()
                .map(this::mapToResponse)   // ✅ FIXED .map ERROR
                .toList();
    }

    // ✅ MAPPER (VERY IMPORTANT - fixes your error)
    private PaymentResponse mapToResponse(Payment payment) {

        PaymentResponse res = new PaymentResponse();

        res.setPaymentId(payment.getPaymentId());
        res.setLoanId(payment.getLoanId());
        res.setPaymentDate(payment.getPaymentDate());
        res.setEmiAmount(payment.getEmiAmount());
        res.setPaidAmount(payment.getPaidAmount());
        res.setDueAmount(payment.getDueAmount());
        res.setFineAmount(payment.getFineAmount());
        res.setDaysCovered(payment.getDaysCovered());
        res.setNextEmiDate(payment.getNextEmiDate());
//        res.setPaymentMethod(payment.getPaymentMethod());
        res.setStatus(payment.getStatus());

        return res;
    }
}
