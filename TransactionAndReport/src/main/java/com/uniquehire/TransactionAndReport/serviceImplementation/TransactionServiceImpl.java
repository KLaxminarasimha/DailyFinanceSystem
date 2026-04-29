package com.uniquehire.TransactionAndReport.serviceImplementation;


import com.uniquehire.TransactionAndReport.dto.TransactionRequestDTO;
import com.uniquehire.TransactionAndReport.dto.TransactionResponseDTO;
import com.uniquehire.TransactionAndReport.dto.TransactionSummaryDTO;
import com.uniquehire.TransactionAndReport.entity.Transaction;
import com.uniquehire.TransactionAndReport.repository.TransactionRepository;
import com.uniquehire.TransactionAndReport.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository repository;

    // 🔥 SAVE TRANSACTION
    @Override
    public TransactionResponseDTO save(TransactionRequestDTO request) {

        Transaction tx = Transaction.builder()
                .referenceId(request.getReferenceId())
                .customerId(request.getCustomerId())
                .amount(request.getAmount())
                .type(request.getType())
                .direction(request.getDirection())
                .status("SUCCESS")
                .timestamp(LocalDateTime.now())
                .build();

        return mapToResponse(repository.save(tx));
    }

    // 🔥 GET BY LOAN
    @Override
    public List<TransactionResponseDTO> getByLoan(Long loanId) {

        List<Transaction> list = repository.findByReferenceId(loanId);

        if (list == null || list.isEmpty()) {
            return List.of(); // avoid null issues
        }

        return list.stream()
                .map(this::mapToResponse)
                .toList();
    }

    // 🔥 SUMMARY (CORE REPORT)
    @Override
    public TransactionSummaryDTO getSummary() {

        List<Transaction> all = repository.findAll();

        BigDecimal totalCredit = all.stream()
                .filter(t -> "CREDIT".equalsIgnoreCase(t.getDirection()))
                .map(t -> t.getAmount() == null ? BigDecimal.ZERO : t.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalDebit = all.stream()
                .filter(t -> "DEBIT".equalsIgnoreCase(t.getDirection()))
                .map(t -> t.getAmount() == null ? BigDecimal.ZERO : t.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        TransactionSummaryDTO summary = new TransactionSummaryDTO();
        summary.setTotalCredit(totalCredit);
        summary.setTotalDebit(totalDebit);
        summary.setProfit(totalCredit.subtract(totalDebit));

        return summary;
    }

    // 🔁 MAPPING
    private TransactionResponseDTO mapToResponse(Transaction tx) {

        TransactionResponseDTO res = new TransactionResponseDTO();

        res.setTransactionId(tx.getTransactionId());
        res.setReferenceId(tx.getReferenceId());
        res.setCustomerId(tx.getCustomerId());
        res.setAmount(tx.getAmount() == null ? BigDecimal.ZERO : tx.getAmount());
        res.setType(tx.getType());
        res.setDirection(tx.getDirection());
        res.setStatus(tx.getStatus());
        res.setTimestamp(tx.getTimestamp());

        return res;
    }
}