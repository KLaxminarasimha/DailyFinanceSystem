package com.uniquehire.TransactionAndReport.service;

import com.uniquehire.TransactionAndReport.dto.TransactionRequestDto;
import com.uniquehire.TransactionAndReport.dto.TransactionResponseDto;

import java.util.List;

public interface TransactionService {
    TransactionResponseDto recordTransaction(TransactionRequestDto request);
    List<TransactionResponseDto> getTransactionByPaymentId(Long paymentId);
}
