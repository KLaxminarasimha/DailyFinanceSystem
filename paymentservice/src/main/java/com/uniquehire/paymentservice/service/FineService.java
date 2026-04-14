package com.uniquehire.paymentservice.service;

import com.uniquehire.paymentservice.dtos.Request.CreateFineRequest;
import com.uniquehire.paymentservice.dtos.Request.UpdateFineStatusRequest;
import com.uniquehire.paymentservice.dtos.Response.FineResponse;

import java.util.List;

public interface FineService {

    FineResponse createFine(CreateFineRequest request);

    List<FineResponse> getFinesByLoan(Long loanId);

    List<FineResponse> getAllFines();

    FineResponse updateFineStatus(Long fineId, UpdateFineStatusRequest request);

    void deleteFine(Long fineId);
}