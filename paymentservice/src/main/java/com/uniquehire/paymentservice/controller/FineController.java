package com.uniquehire.paymentservice.controller;

import com.uniquehire.paymentservice.constants.MessageConstants;
import com.uniquehire.paymentservice.dtos.Request.CreateFineRequest;
import com.uniquehire.paymentservice.dtos.Request.UpdateFineStatusRequest;
import com.uniquehire.paymentservice.dtos.Response.ApiResponse;
import com.uniquehire.paymentservice.dtos.Response.FineResponse;
import com.uniquehire.paymentservice.service.FineService;
import com.uniquehire.paymentservice.enums.FineStatus;
//import com.uniquehire.paymentservice.utils.PaymentUtils;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.uniquehire.paymentservice.constants.MessageConstants.FINE_LIST;
import static com.uniquehire.paymentservice.constants.MessageConstants.FINE_UPDATED;

@RestController
@RequestMapping("/fines")
public class FineController {

    private FineService fineService;

    // Constructor injection (no Lombok)
    public FineController(FineService fineService) {
        this.fineService = fineService;
    }

    // ✅ GET: Get fines by loanId
    @GetMapping("/{loanId}")
    public ApiResponse<List<FineResponse>> getFines(@PathVariable Long loanId) {

        List<FineResponse> fines = fineService.getFines(loanId);

        return ApiResponse.success(
                FINE_LIST,
                fines,
                200
        );
    }

    // ✅ PUT: Update fine status (pay / waive)
    @PutMapping("/{fineId}/status")
    public ApiResponse<FineResponse> updateFineStatus(
            @PathVariable Long fineId,
            @RequestBody UpdateFineStatusRequest request
    ) {

        FineResponse response = fineService.updateStatus(fineId, request);

        return ApiResponse.success(
                FINE_UPDATED,
                response,
                200
        );
    }
}