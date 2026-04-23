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

import java.util.ArrayList;
import java.util.List;

@Service
public class FineServiceImpl implements FineService {

    private FineRepository fineRepository;

    // Constructor injection (manual, since no Lombok)
    public FineServiceImpl(FineRepository fineRepository) {
        this.fineRepository = fineRepository;
    }

    @Override
    public FineResponse createFine(CreateFineRequest request) {

        Fine fine = new Fine();
        fine.setLoanId(request.getLoanId());
        fine.setFineAmount(request.getFineAmount());
        fine.setReason(request.getReason());
        fine.setDate(request.getFineDate());
        fine.setStatus(FineStatus.PENDING);

        fineRepository.save(fine);

        return map(fine);
    }

    @Override
    public List<FineResponse> getFines(Long loanId) {

        List<Fine> fines = fineRepository.findByLoanId(loanId);
        List<FineResponse> responseList = new ArrayList<>();

        for (Fine fine : fines) {
            responseList.add(map(fine));
        }

        return responseList;
    }

    @Override
    public FineResponse updateStatus(Long fineId, UpdateFineStatusRequest request) {

        Fine fine = fineRepository.findById(fineId)
                .orElseThrow(() -> new ResourceNotFoundException("Fine not found"));

        fine.setStatus(request.getStatus());

        fineRepository.save(fine);

        return map(fine);
    }

    // Simple mapping method (no builder)
    private FineResponse map(Fine fine) {

        FineResponse response = new FineResponse();

        response.setFineId(fine.getFineId());
        response.setLoanId(fine.getLoanId());
        response.setFineAmount(fine.getFineAmount());
        response.setReason(fine.getReason());
        response.setFineDate(fine.getDate());
        response.setStatus(fine.getStatus());

        return response;
    }
}