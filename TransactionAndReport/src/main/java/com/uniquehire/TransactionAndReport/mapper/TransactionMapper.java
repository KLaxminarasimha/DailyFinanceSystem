package com.uniquehire.TransactionAndReport.mapper;

import com.uniquehire.TransactionAndReport.dto.*;
import com.uniquehire.TransactionAndReport.entity.*;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public TransactionResponseDto toDto(Transaction transaction) {
        if (transaction == null) {
            return null;
        }

        TransactionResponseDto dto = new TransactionResponseDto();

        dto.setTransactionId(transaction.getTransactionId());
        dto.setPaymentId(transaction.getPaymentId());
        dto.setLoanId(transaction.getLoanId());
        dto.setCustomerId(transaction.getCustomerId());
        dto.setAgentId(transaction.getAgentId());
        dto.setAmount(transaction.getAmount());
        dto.setGateway(transaction.getGateway());
        dto.setStatus(transaction.getStatus());
        dto.setTimestamp(transaction.getTimestamp());

        return dto;
    }
}
