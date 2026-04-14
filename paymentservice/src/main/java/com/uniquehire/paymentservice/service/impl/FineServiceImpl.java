package com.uniquehire.paymentservice.service.impl;

import com.uniquehire.paymentservice.constants.MessageConstants;
import com.uniquehire.paymentservice.dtos.Request.CreateFineRequest;
import com.uniquehire.paymentservice.dtos.Request.UpdateFineStatusRequest;
import com.uniquehire.paymentservice.dtos.Response.FineResponse;
import com.uniquehire.paymentservice.entity.Fine;
import com.uniquehire.paymentservice.entity.Payment;
import com.uniquehire.paymentservice.enums.FineStatus;
import com.uniquehire.paymentservice.exception.BusinessException;
import com.uniquehire.paymentservice.exception.ResourceNotFoundException;
import com.uniquehire.paymentservice.repository.FineRepository;
import com.uniquehire.paymentservice.repository.PaymentRepository;
import com.uniquehire.paymentservice.service.FineService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class FineServiceImpl implements FineService {

    private final FineRepository fineRepository;
    private final PaymentRepository paymentRepository;

    public FineServiceImpl(FineRepository fineRepository, PaymentRepository paymentRepository) {
        this.fineRepository = fineRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public FineResponse createFine(CreateFineRequest request) {

        if (request.getFineDate() != null && request.getFineDate().isAfter(LocalDate.now())) {
            throw new BusinessException(MessageConstants.INVALID_FINE_DATE);
        }

        if (request.getFineAmount() == null || request.getFineAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(MessageConstants.INVALID_FINE_AMOUNT);
        }

        Fine fine = new Fine();
        fine.setLoanId(request.getLoanId());
        fine.setFineAmount(request.getFineAmount());
        fine.setReason(request.getReason());
        fine.setFineDate(request.getFineDate());
        fine.setStatus(FineStatus.PENDING);

        if (request.getPaymentId() != null) {
            Payment payment = paymentRepository.findById(request.getPaymentId())
                    .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.PAYMENT_NOT_FOUND));
            fine.setPayment(payment);
        }

        Fine savedFine = fineRepository.save(fine);
        return mapToFineResponse(savedFine);
    }

    @Override
    public List<FineResponse> getFinesByLoan(Long loanId) {
        List<Fine> fineList = fineRepository.findByLoanIdOrderByFineDateAsc(loanId);
        List<FineResponse> responseList = new ArrayList<>();

        for (Fine fine : fineList) {
            responseList.add(mapToFineResponse(fine));
        }

        return responseList;
    }

    @Override
    public List<FineResponse> getAllFines() {
        List<Fine> fineList = fineRepository.findAll();
        List<FineResponse> responseList = new ArrayList<>();

        for(Fine fine : fineList) {
            responseList.add(mapToFineResponse(fine));
        }
        return responseList;
    }

    @Override
    public FineResponse updateFineStatus(Long fineId, UpdateFineStatusRequest request) {
        Fine fine = fineRepository.findById(fineId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.FINE_NOT_FOUND));

        fine.setStatus(request.getStatus());

        if (request.getPaymentId() != null) {
            Payment payment = paymentRepository.findById(request.getPaymentId())
                    .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.PAYMENT_NOT_FOUND));
            fine.setPayment(payment);
        }

        Fine updatedFine = fineRepository.save(fine);
        return mapToFineResponse(updatedFine);
    }

    @Override
    public void deleteFine(Long fineId) {
        Fine fine = fineRepository.findById(fineId)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.FINE_NOT_FOUND));

        fineRepository.delete(fine);
    }


    private FineResponse mapToFineResponse(Fine fine) {
        FineResponse response = new FineResponse();
        response.setFineId(fine.getFineId());
        response.setLoanId(fine.getLoanId());
        response.setPaymentId(fine.getPayment() != null ? fine.getPayment().getPaymentId() : null);
        response.setFineAmount(fine.getFineAmount());
        response.setReason(fine.getReason());
        response.setFineDate(fine.getFineDate());
        response.setStatus(fine.getStatus());
        return response;
    }
}