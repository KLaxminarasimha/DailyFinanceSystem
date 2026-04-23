package com.uniquehire.paymentservice.service;

import com.uniquehire.paymentservice.dtos.Request.CreateFineRequest;
import com.uniquehire.paymentservice.dtos.Request.UpdateFineStatusRequest;
import com.uniquehire.paymentservice.dtos.Response.FineResponse;
import com.uniquehire.paymentservice.enums.FineStatus;

import java.util.List;

public interface FineService {

    FineResponse createFine(CreateFineRequest request);

    List<FineResponse> getFines(Long loanId);

    FineResponse updateStatus(Long fineId, UpdateFineStatusRequest request);
}