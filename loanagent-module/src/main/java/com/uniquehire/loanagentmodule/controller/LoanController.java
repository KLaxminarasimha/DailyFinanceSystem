package com.uniquehire.loanagentmodule.controller;

import com.uniquehire.loanagentmodule.dto.Request.LoanRequestDTO;
import com.uniquehire.loanagentmodule.dto.Response.LoanResponseDTO;
import com.uniquehire.loanagentmodule.service.LoanService;
import com.uniquehire.loanagentmodule.utils.ApiResponse;

import com.uniquehire.loanagentmodule.utils.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    // Create Loan
    @PostMapping
    public ResponseEntity<ApiResponse<LoanResponseDTO>> createLoan(
            @Valid @RequestBody LoanRequestDTO request) {

        LoanResponseDTO loan = loanService.createLoan(request);

        return ResponseUtil.success(loan, "Loan created successfully", HttpStatus.CREATED);
    }

    // Get All Loans
    @GetMapping
    public ResponseEntity<ApiResponse<List<LoanResponseDTO>>> getAllLoans() {

        List<LoanResponseDTO> loans = loanService.getAllLoans();

        return ResponseUtil.success(loans, "Loans retrieved successfully", HttpStatus.OK);
    }

    // Get Loan By Id
    @GetMapping("/{loanId}")
    public ResponseEntity<ApiResponse<LoanResponseDTO>> getLoanById(
            @PathVariable Long loanId) {

        LoanResponseDTO loan = loanService.getLoan(loanId);

        return ResponseUtil.success(loan, "Loan retrieved successfully", HttpStatus.OK);
    }

    // Update Loan Status
    @PutMapping("/{loanId}/status")
    public ResponseEntity<ApiResponse<String>> updateLoanStatus(
            @PathVariable Long loanId,
            @RequestParam String status,
            @RequestParam(required = false) String remarks) {

        loanService.updateLoanStatus(loanId, status, remarks);

        return ResponseUtil.success("Status updated", "Loan status updated successfully", HttpStatus.OK);
    }
}
