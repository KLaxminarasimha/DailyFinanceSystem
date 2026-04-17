
package com.uniquehire.paymentservice.service.impl;

import com.uniquehire.paymentservice.constants.MessageConstants;
import com.uniquehire.paymentservice.dtos.Request.CreatePaymentRequest;
import com.uniquehire.paymentservice.dtos.Request.UpdatePaymentStatusRequest;
import com.uniquehire.paymentservice.dtos.Response.LoanDetails;
import com.uniquehire.paymentservice.dtos.Response.PaymentResponse;
import com.uniquehire.paymentservice.entity.Fine;
import com.uniquehire.paymentservice.entity.Payment;
import com.uniquehire.paymentservice.enums.FineStatus;
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

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final FineRepository fineRepository;
    private final RestTemplate restTemplate;

    @Value("${loan.service.base-url}")
    private String loanServiceBaseUrl;

    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              FineRepository fineRepository,
                              RestTemplate restTemplate) {
        this.paymentRepository = paymentRepository;
        this.fineRepository = fineRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public PaymentResponse recordPayment(CreatePaymentRequest request) {

        // 1. validate payment date
        if (request.getPaymentDate() != null && request.getPaymentDate().isAfter(LocalDate.now())) {
            throw new BusinessException(MessageConstants.INVALID_PAYMENT_DATE);
        }

        // 2. get loan details from loan module
        LoanDetails loanDetails = getLoanDetails(request.getLoanId());

        if (loanDetails.getLoanStatus() != null &&
                "CLOSED".equalsIgnoreCase(loanDetails.getLoanStatus())) {
            throw new BusinessException(MessageConstants.LOAN_CLOSED);
        }

        // 3. EMI and user entered amount
        BigDecimal emiAmount = loanDetails.getDailyEmi();
        BigDecimal enteredAmount = request.getPaidAmount();

        validatePaymentAmount(enteredAmount);

        // 4. calculate expected payment date
        LocalDate expectedPaymentDate = getExpectedPaymentDate(loanDetails);

        long overdueDays = 0;
        BigDecimal fineAmount = BigDecimal.ZERO;

        // 5. fine calculation = 1% of total loan amount * overdue days
        if (request.getPaymentDate().isAfter(expectedPaymentDate)) {
            overdueDays = ChronoUnit.DAYS.between(expectedPaymentDate, request.getPaymentDate());

            BigDecimal percentage = new BigDecimal("0.01");

            fineAmount = loanDetails.getTotalAmount()
                    .multiply(percentage)
                    .multiply(BigDecimal.valueOf(overdueDays));
        }

        // 6. total payable = entered amount + fine
        BigDecimal totalPayableAmount = enteredAmount.add(fineAmount);

        // 7. generate upi link if payment method is UPI
        String upiLink = null;
        if (request.getPaymentMethod() != null &&
                request.getPaymentMethod().name().equalsIgnoreCase("UPI")) {
            upiLink = generateUpiLink(totalPayableAmount);
        }

        // 8. save payment
        Payment payment = new Payment();
        payment.setLoanId(request.getLoanId());
        payment.setPaymentDate(request.getPaymentDate());
        payment.setEmiAmount(emiAmount);               // EMI shown to user
        payment.setPaidAmount(totalPayableAmount);     // final amount to pay
        payment.setFine(fineAmount);                   // separate fine
        payment.setStatus(PaymentStatus.COMPLETED);
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setReferenceId(request.getReferenceId());
        payment.setDeleted(false);

        Payment savedPayment = paymentRepository.save(payment);

        // 9. save fine record if fine exists
        if (fineAmount.compareTo(BigDecimal.ZERO) > 0) {
            Fine fine = new Fine();
            fine.setLoanId(request.getLoanId());
            fine.setPayment(savedPayment);
            fine.setFineAmount(fineAmount);
            fine.setReason("Late payment by " + overdueDays + " day(s)");
            fine.setFineDate(request.getPaymentDate());
            fine.setStatus(FineStatus.PENDING);
            fineRepository.save(fine);
        }

        // 10. running total paid
        BigDecimal totalPaid = loanDetails.getAmountPaid().add(totalPayableAmount);

        return mapToPaymentResponse(savedPayment, totalPaid, enteredAmount, overdueDays, upiLink);
    }

    @Override
    public List<PaymentResponse> getPaymentsByLoan(Long loanId) {
        List<Payment> payments = paymentRepository.findByLoanIdAndIsDeletedFalse(loanId);
        List<PaymentResponse> responseList = new ArrayList<>();

        BigDecimal runningTotal = BigDecimal.ZERO;

        for (Payment payment : payments) {
            runningTotal = runningTotal.add(payment.getPaidAmount());
            responseList.add(mapToPaymentResponse(payment, runningTotal, null, null, null));
        }

        return responseList;
    }

    @Override
    public List<PaymentResponse> getAllPayments() {
        List<Payment> payments = paymentRepository.findByIsDeletedFalse();
        List<PaymentResponse> responseList = new ArrayList<>();

        BigDecimal runningTotal = BigDecimal.ZERO;

        for (Payment payment : payments) {
            runningTotal = runningTotal.add(payment.getPaidAmount());
            responseList.add(mapToPaymentResponse(payment, runningTotal, null, null, null));
        }

        return responseList;
    }

    @Override
    public PaymentResponse updatePaymentStatus(Long paymentId, UpdatePaymentStatusRequest request) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.PAYMENT_NOT_FOUND));

        if (payment.isDeleted()) {
            throw new ResourceNotFoundException(MessageConstants.PAYMENT_NOT_FOUND);
        }

        payment.setStatus(request.getStatus());
        Payment updatedPayment = paymentRepository.save(payment);

        return mapToPaymentResponse(updatedPayment, updatedPayment.getPaidAmount(), null, null, null);
    }

    @Override
    public void deletePayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.PAYMENT_NOT_FOUND));

        if (payment.isDeleted()) {
            throw new ResourceNotFoundException(MessageConstants.PAYMENT_NOT_FOUND);
        }

        payment.setDeleted(true);
        paymentRepository.save(payment);
    }

    @Override
    public void validatePaymentAmount(BigDecimal paidAmount) {
        if (paidAmount == null || paidAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(MessageConstants.INVALID_PAID_AMOUNT);
        }
    }

    private LoanDetails getLoanDetails(Long loanId) {
        try {
            String url = loanServiceBaseUrl + "/" + loanId;
            System.out.println("Loan URL: " + url);

            Map response = restTemplate.getForObject(url, Map.class);
            System.out.println("Loan Response: " + response);

            if (response == null || response.get("data") == null) {
                throw new ResourceNotFoundException("Loan not found");
            }

            Map data = (Map) response.get("data");

            LoanDetails loan = new LoanDetails();
            loan.setLoanId(Long.valueOf(data.get("loanId").toString()));
            loan.setLoanStatus(data.get("status").toString());
            loan.setDailyEmi(new BigDecimal(data.get("dailyEmi").toString()));
            loan.setStartDate(LocalDate.parse(data.get("startDate").toString()));
            loan.setEndDate(LocalDate.parse(data.get("endDate").toString()));

            // friend module not sending amountPaid now
            loan.setAmountPaid(BigDecimal.ZERO);

            loan.setTotalFine(new BigDecimal(data.get("totalFine").toString()));
            loan.setTotalAmount(new BigDecimal(data.get("totalAmount").toString()));

            return loan;
        } catch (Exception ex) {
            throw new ResourceNotFoundException("Loan not found");
        }
    }

    private LocalDate getExpectedPaymentDate(LoanDetails loanDetails) {
        long paidInstallments = 0;

        if (loanDetails.getDailyEmi() != null
                && loanDetails.getDailyEmi().compareTo(BigDecimal.ZERO) > 0
                && loanDetails.getAmountPaid() != null) {
            paidInstallments = loanDetails.getAmountPaid()
                    .divide(loanDetails.getDailyEmi(), 0, java.math.RoundingMode.DOWN)
                    .longValue();
        }

        return loanDetails.getStartDate().plusDays(paidInstallments);
    }

    private String generateUpiLink(BigDecimal amount) {
        String upiId = "yourupiid@upi";   // change this
        String payeeName = "LoanPayment";

        return "upi://pay?pa=" + upiId
                + "&pn=" + payeeName
                + "&am=" + amount
                + "&cu=INR";
    }

    private PaymentResponse mapToPaymentResponse(Payment payment,
                                                 BigDecimal totalPaid,
                                                 BigDecimal enteredAmount,
                                                 Long overdueDays,
                                                 String upiLink) {
        PaymentResponse response = new PaymentResponse();
        response.setPaymentId(payment.getPaymentId());
        response.setLoanId(payment.getLoanId());
        response.setPaymentDate(payment.getPaymentDate());
        response.setEmiAmount(payment.getEmiAmount());
        response.setPaidAmount(payment.getPaidAmount());
        response.setFine(payment.getFine());
        response.setTotalPaid(totalPaid);
        response.setStatus(payment.getStatus());
        response.setPaymentMethod(payment.getPaymentMethod());
        response.setReferenceId(payment.getReferenceId());

        // these fields should exist in PaymentResponse
        response.setEnteredAmount(enteredAmount);
        response.setOverdueDays(overdueDays);
        response.setUpiLink(upiLink);

        return response;
    }
}
