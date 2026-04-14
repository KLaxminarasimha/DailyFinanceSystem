package com.uniquehire.paymentservice.controller;

import com.uniquehire.paymentservice.constants.MessageConstants;
import com.uniquehire.paymentservice.dtos.Request.CreateFineRequest;
import com.uniquehire.paymentservice.dtos.Request.UpdateFineStatusRequest;
import com.uniquehire.paymentservice.dtos.Response.ApiResponse;
import com.uniquehire.paymentservice.dtos.Response.FineResponse;
import com.uniquehire.paymentservice.service.FineService;
import com.uniquehire.paymentservice.utils.ResponseUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fines")
public class FineController {

    private final FineService fineService;

    public FineController(FineService fineService) {
        this.fineService = fineService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<FineResponse>> createFine(@Valid @RequestBody CreateFineRequest request) {
        FineResponse response = fineService.createFine(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseUtil.success(
                        MessageConstants.FINE_RECORDED_SUCCESS,
                        response,
                        HttpStatus.CREATED.value()
                ));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<FineResponse>>> getAllFines() {
        List<FineResponse> response = fineService.getAllFines();
        return ResponseEntity.ok(
                ResponseUtil.success(
                        MessageConstants.ALL_FINES_FETCHED_SUCCESS,
                        response,
                        HttpStatus.OK.value()
                )
        );
    }

    @GetMapping("/{loanId}")
    public ResponseEntity<ApiResponse<List<FineResponse>>> getFinesByLoan(@PathVariable Long loanId) {
        List<FineResponse> response = fineService.getFinesByLoan(loanId);
        return ResponseEntity.ok(
                ResponseUtil.success(
                        MessageConstants.FINES_FETCHED_SUCCESS,
                        response,
                        HttpStatus.OK.value()
                )
        );
    }

    @PutMapping("/{fineId}/status")
    public ResponseEntity<ApiResponse<FineResponse>> updateFineStatus(@PathVariable Long fineId,
                                                                      @Valid @RequestBody UpdateFineStatusRequest request) {
        FineResponse response = fineService.updateFineStatus(fineId, request);
        return ResponseEntity.ok(
                ResponseUtil.success(
                        MessageConstants.FINE_STATUS_UPDATED_SUCCESS,
                        response,
                        HttpStatus.OK.value()
                )
        );
    }

    @DeleteMapping("/{fineId}")
    public ResponseEntity<ApiResponse<Object>> deleteFine(@PathVariable Long fineId) {
        fineService.deleteFine(fineId);
        return ResponseEntity.ok(
                ResponseUtil.success(
                       MessageConstants.FINE_DELETED_SUCCESS,
                        null,
                        HttpStatus.OK.value()
                )
        );
    }

}
