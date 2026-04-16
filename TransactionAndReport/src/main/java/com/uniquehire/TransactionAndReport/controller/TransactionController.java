package com.uniquehire.TransactionAndReport.controller;

import com.uniquehire.TransactionAndReport.dto.TransactionRequestDto;
import com.uniquehire.TransactionAndReport.dto.TransactionResponseDto;
import com.uniquehire.TransactionAndReport.service.TransactionService;
import com.uniquehire.TransactionAndReport.util.ResponseUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<?> recordTransaction(
            @Valid @RequestBody TransactionRequestDto request){
        TransactionResponseDto response = transactionService.recordTransaction(request);
        return ResponseUtil.success(response, "Transaction recorded successfully");
    }

    @GetMapping("/payment")
    public ResponseEntity<?> getTransactionByPaymentId(
            @PathVariable Long paymentId){
        List<TransactionResponseDto> response = transactionService.getTransactionByPaymentId(paymentId);

        return ResponseUtil.success(response, "Transactions fetched successfully");
    }
}
