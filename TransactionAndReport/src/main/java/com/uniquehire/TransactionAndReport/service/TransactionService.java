package com.uniquehire.TransactionAndReport.service;

import com.uniquehire.TransactionAndReport.dto.TransactionRequestDTO;
import com.uniquehire.TransactionAndReport.dto.TransactionResponseDTO;
import com.uniquehire.TransactionAndReport.dto.TransactionSummaryDTO;

import java.util.List;

public interface TransactionService {

    TransactionResponseDTO save(TransactionRequestDTO request);

    List<TransactionResponseDTO> getByLoan(Long loanId);

    TransactionSummaryDTO getSummary();
}