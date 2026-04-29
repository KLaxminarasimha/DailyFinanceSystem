package com.uniquehire.paymentservice.service.impl;

import com.uniquehire.paymentservice.dtos.Request.FundRequestDTO;
import com.uniquehire.paymentservice.dtos.Request.PaymentRequestDTO;
import com.uniquehire.paymentservice.dtos.Response.Customer;
import com.uniquehire.paymentservice.dtos.Response.LoanResponseDTO;
import com.uniquehire.paymentservice.dtos.Response.PaymentResponseDTO;
import com.uniquehire.paymentservice.dtos.Request.TransactionRequestDTO;
import com.uniquehire.paymentservice.entity.Payment;
import com.uniquehire.paymentservice.repository.PaymentRepository;
import com.uniquehire.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final RestTemplate restTemplate;

    @Override
    public PaymentResponseDTO makePayment(Long userId, PaymentRequestDTO request) {

        // 🔥 1️⃣ GET CUSTOMER ID
        Long customerId = getCustomerId(userId);

        // 🔥 2️⃣ GET LOAN
        LoanResponseDTO loan = restTemplate.getForObject(
                "http://loan-service/loans/" + request.getLoanId(),
                LoanResponseDTO.class
        );

        if (loan == null) {
            throw new RuntimeException("Loan not found");
        }

        if (!loan.getCustomerId().equals(customerId)) {
            throw new RuntimeException("Unauthorized payment");
        }

        BigDecimal paid = request.getAmount();
        BigDecimal dailyEmi = loan.getDailyEmi();

        BigDecimal due = loan.getDueAmount() == null ? BigDecimal.ZERO : loan.getDueAmount();
        BigDecimal fine = loan.getFineAmount() == null ? BigDecimal.ZERO : loan.getFineAmount();

        String type = "EMI";

        // 🔴 PARTIAL PAYMENT
        if (paid.compareTo(dailyEmi) < 0) {

            BigDecimal remaining = dailyEmi.subtract(paid);
            due = due.add(remaining);

            // 🔥 1% fine
            BigDecimal penalty = dailyEmi.multiply(BigDecimal.valueOf(0.01));
            fine = fine.add(penalty);

            type = "PARTIAL";
        }

        // 🟢 ADVANCE PAYMENT
        else if (paid.compareTo(dailyEmi) > 0) {

            BigDecimal extra = paid.subtract(dailyEmi);

            // 1️⃣ clear due
            if (due.compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal used = extra.min(due);
                due = due.subtract(used);
                extra = extra.subtract(used);
            }

            // 2️⃣ clear fine
            if (extra.compareTo(BigDecimal.ZERO) > 0 && fine.compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal used = extra.min(fine);
                fine = fine.subtract(used);
                extra = extra.subtract(used);
            }

            // 3️⃣ remaining extra will reduce principal (handled in loan-service)
            type = "ADVANCE";
        }

        // 🟢 FULL EMI → nothing to change (type stays EMI)

        // 🔥 FUND SERVICE
        callFundService(paid, request.getLoanId());

        // 🔥 UPDATE LOAN (IMPORTANT → remainingDays handled there)
        restTemplate.put(
                "http://loan-service/loans/update-payment?loanId="
                        + request.getLoanId()
                        + "&paid=" + paid
                        + "&due=" + due
                        + "&fine=" + fine,
                null
        );

        // 🔥 SAVE PAYMENT
        Payment payment = Payment.builder()
                .loanId(request.getLoanId())
                .customerId(customerId)
                .amount(paid)
                .type(type)
                .status("SUCCESS")
                .paymentDate(LocalDateTime.now())
                .build();

        Payment saved = paymentRepository.save(payment);

        // 🔥 TRANSACTION SERVICE
        callTransactionService(
                request.getLoanId(),
                customerId,
                paid,
                type
        );

        return mapToResponse(saved);
    }

    // 🔥 GET CUSTOMER ID
    private Long getCustomerId(Long userId) {

        Customer customer = restTemplate.getForObject(
                "http://customer-service/customers/user/" + userId,
                Customer.class
        );

        if (customer == null) {
            throw new RuntimeException("Customer not found");
        }

        return customer.getId();
    }

    // 🔧 FUND CALL
    private void callFundService(BigDecimal amount, Long loanId) {

        FundRequestDTO req = new FundRequestDTO();
        req.setAmount(amount);
        req.setReferenceId(loanId);

        restTemplate.postForObject(
                "http://fund-service/fund/emi",
                req,
                String.class
        );
    }

    // 🔧 TRANSACTION CALL
    private void callTransactionService(Long loanId,
                                        Long customerId,
                                        BigDecimal amount,
                                        String type) {

        TransactionRequestDTO tx = new TransactionRequestDTO(
                loanId,
                customerId,
                amount,
                type,
                "CREDIT"
        );

        restTemplate.postForObject(
                "http://transaction-service/transactions",
                tx,
                String.class
        );
    }

    private PaymentResponseDTO mapToResponse(Payment payment) {

        PaymentResponseDTO res = new PaymentResponseDTO();

        res.setPaymentId(payment.getPaymentId());
        res.setLoanId(payment.getLoanId());
        res.setCustomerId(payment.getCustomerId());
        res.setAmount(payment.getAmount());
        res.setType(payment.getType());
        res.setStatus(payment.getStatus());
        res.setPaymentDate(payment.getPaymentDate());

        return res;
    }

    @Override
    public List<PaymentResponseDTO> getPayments(Long loanId) {
        return paymentRepository.findByLoanId(loanId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
}