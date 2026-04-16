package com.uniquehire.TransactionAndReport.serviceImplementation;

import com.uniquehire.TransactionAndReport.dto.TransactionRequestDto;
import com.uniquehire.TransactionAndReport.dto.TransactionResponseDto;
import com.uniquehire.TransactionAndReport.entity.Transaction;
import com.uniquehire.TransactionAndReport.mapper.TransactionMapper;
import com.uniquehire.TransactionAndReport.repository.TransactionRepository;
import com.uniquehire.TransactionAndReport.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionMapper transactionMapper;

    @Override
    public TransactionResponseDto recordTransaction(TransactionRequestDto request) {
        validatePaymentWithExternalService(request.getPaymentId());
        Long loanId = fetchLoanDetailsFromLoanService(request.getPaymentId());
        Transaction transaction = new Transaction();
        transaction.setPaymentId(request.getPaymentId());
        transaction.setLoanId(loanId);
        transaction.setAmount(request.getAmount());
        transaction.setGateway(request.getGateway());
        transaction.setStatus(request.getTransactionStatus());
        transaction.setTimestamp(LocalDateTime.now());

        Transaction saved = transactionRepository.save(transaction);

        return buildTransactionResponse(saved);

    }

    @Override
    public List<TransactionResponseDto> getTransactionByPaymentId(Long paymentId) {
        return transactionRepository.findByPaymentId(paymentId)
                .stream()
                .map(this::buildTransactionResponse)
                .collect(Collectors.toList());
    }

    private void validatePaymentWithExternalService(Long paymentId){
        if (paymentId == null){
            throw new RuntimeException("invalid Payment ID");
        }
    }

    private Long fetchLoanDetailsFromLoanService(Long paymentId){
        return 101L;
    }

    private TransactionResponseDto buildTransactionResponse(Transaction transaction){
        return transactionMapper.toDto(transaction);
    }
}
