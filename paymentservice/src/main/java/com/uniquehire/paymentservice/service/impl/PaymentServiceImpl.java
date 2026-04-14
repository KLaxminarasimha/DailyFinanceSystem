package com.uniquehire.paymentservice.service.impl;

import com.uniquehire.paymentservice.constants.MessageConstants;
import com.uniquehire.paymentservice.constants.AppConstants;
import com.uniquehire.paymentservice.dtos.Request.CreatePaymentRequest;
import com.uniquehire.paymentservice.dtos.Request.UpdatePaymentStatusRequest;
import com.uniquehire.paymentservice.dtos.Response.PaymentResponse;
import com.uniquehire.paymentservice.entity.Fine;
import com.uniquehire.paymentservice.entity.Payment;
import com.uniquehire.paymentservice.enums.FineStatus;
import com.uniquehire.paymentservice.enums.PaymentStatus;
import com.uniquehire.paymentservice.exception.BusinessException;
import com.uniquehire.paymentservice.exception.ResourceNotFoundException;
import com.uniquehire.paymentservice.repository.FineRepository;
import com.uniquehire.paymentservice.repository.PaymentRepository;
import com.uniquehire.paymentservice.service.LoanIntegrationService;
import com.uniquehire.paymentservice.service.PaymentService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final FineRepository fineRepository;
    private final LoanIntegrationService loanIntegrationService;

    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              FineRepository fineRepository,
                              LoanIntegrationService loanIntegrationService) {
        this.paymentRepository = paymentRepository;
        this.fineRepository = fineRepository;
        this.loanIntegrationService = loanIntegrationService;
    }

    @Override
    public PaymentResponse recordPayment(CreatePaymentRequest request) {

        if (request.getPaymentDate() != null && request.getPaymentDate().isAfter(LocalDate.now())) {
            throw new BusinessException(MessageConstants.INVALID_PAYMENT_DATE);
        }

        LoanIntegrationService.LoanDetails loanDetails = loanIntegrationService.getLoanDetails(request.getLoanId());

        if (loanDetails.getLoanStatus() != null &&
                "CLOSED".equalsIgnoreCase(loanDetails.getLoanStatus())) {
            throw new BusinessException(MessageConstants.LOAN_CLOSED);
        }

        validatePaymentAmount(request.getPaidAmount(), loanDetails.getDailyEmi());

        LocalDate expectedPaymentDate = getExpectedPaymentDate(loanDetails);

        long overdueDays = 0;
        BigDecimal fineAmount = BigDecimal.ZERO;

        if (request.getPaymentDate().isAfter(expectedPaymentDate)) {
            overdueDays = ChronoUnit.DAYS.between(expectedPaymentDate, request.getPaymentDate());
            fineAmount = BigDecimal.valueOf(overdueDays);
            //fineAmount = BigDecimal.valueOf(overdueDays)
            //        .multiply(AppConstants.FINE_PER_DAY);
        }

        Payment payment = new Payment();
        payment.setLoanId(request.getLoanId());
        payment.setPaymentDate(request.getPaymentDate());
        payment.setEmiAmount(loanDetails.getDailyEmi());
        payment.setPaidAmount(request.getPaidAmount());
        payment.setFine(fineAmount);
        payment.setStatus(PaymentStatus.COMPLETED);
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setReferenceId(request.getReferenceId());

        Payment savedPayment = paymentRepository.save(payment);

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

        loanIntegrationService.updateLoanAfterPayment(
                request.getLoanId(),
                request.getPaidAmount(),
                fineAmount,
                request.getPaymentDate()
        );

        BigDecimal totalPaid = loanDetails.getAmountPaid().add(request.getPaidAmount());

        return mapToPaymentResponse(savedPayment, totalPaid);
    }

    @Override
    public List<PaymentResponse> getPaymentsByLoan(Long loanId) {
        List<Payment> payments = paymentRepository.findByLoanIdOrderByPaymentDateAsc(loanId);
        List<PaymentResponse> responseList = new ArrayList<>();

        BigDecimal runningTotal = BigDecimal.ZERO;

        for (Payment payment : payments) {
            runningTotal = runningTotal.add(payment.getPaidAmount());
            responseList.add(mapToPaymentResponse(payment, runningTotal));
        }

        return responseList;
    }

    @Override
    public List<PaymentResponse> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        List<PaymentResponse> responseList = new ArrayList<>();

        BigDecimal runningTotal = BigDecimal.ZERO;

        for (Payment payment : payments) {
            runningTotal = runningTotal.add(payment.getPaidAmount());
            responseList.add(mapToPaymentResponse(payment, runningTotal));
        }

        return responseList;
    }


    @Override
    public PaymentResponse updatePaymentStatus(Long paymentId, UpdatePaymentStatusRequest request) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.PAYMENT_NOT_FOUND));

        payment.setStatus(request.getStatus());
        Payment updatedPayment = paymentRepository.save(payment);

        return mapToPaymentResponse(updatedPayment, updatedPayment.getPaidAmount());
    }

    @Override
    public void deletePayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.PAYMENT_NOT_FOUND));

        paymentRepository.delete(payment);
    }



    @Override
    public void validatePaymentAmount(BigDecimal paidAmount, BigDecimal emiAmount) {
        if (paidAmount == null || paidAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(MessageConstants.INVALID_PAID_AMOUNT);
        }

        if (paidAmount.compareTo(emiAmount) > 0) {
            throw new BusinessException(MessageConstants.PAYMENT_EXCEEDS_EMI);
        }
    }

    private LocalDate getExpectedPaymentDate(LoanIntegrationService.LoanDetails loanDetails) {
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

    private PaymentResponse mapToPaymentResponse(Payment payment, BigDecimal totalPaid) {
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

        return response;
    }
}