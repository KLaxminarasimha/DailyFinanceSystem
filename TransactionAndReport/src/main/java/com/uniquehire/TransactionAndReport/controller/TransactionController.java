package com.uniquehire.TransactionAndReport.controller;

import com.uniquehire.TransactionAndReport.dto.TransactionRequestDTO;
import com.uniquehire.TransactionAndReport.dto.TransactionResponseDTO;
import com.uniquehire.TransactionAndReport.dto.TransactionSummaryDTO;
import com.uniquehire.TransactionAndReport.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService service;

    // 🔥 SAVE TRANSACTION
    @PostMapping
    public TransactionResponseDTO save(@RequestBody TransactionRequestDTO request) {
        return service.save(request);
    }

    // 🔍 GET BY LOAN
    @GetMapping("/{loanId}")
    public List<TransactionResponseDTO> getByLoan(@PathVariable Long loanId) {
        return service.getByLoan(loanId);
    }

    // 📊 SUMMARY REPORT
    @GetMapping("/summary")
    public TransactionSummaryDTO getSummary() {
        return service.getSummary();
    }
}