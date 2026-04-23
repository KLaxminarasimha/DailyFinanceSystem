package com.uniquehire.TransactionAndReport.serviceImplementation;

import com.uniquehire.TransactionAndReport.dto.External.ExternalLoanDto;
import com.uniquehire.TransactionAndReport.dto.External.ExternalPaymentDto;
import com.uniquehire.TransactionAndReport.dto.TransactionRequestDto;
import com.uniquehire.TransactionAndReport.dto.TransactionResponseDto;
import com.uniquehire.TransactionAndReport.entity.Transaction;
import com.uniquehire.TransactionAndReport.exception.ResourceNotFoundException;
import com.uniquehire.TransactionAndReport.exception.ValidationException;
import com.uniquehire.TransactionAndReport.mapper.TransactionMapper;
import com.uniquehire.TransactionAndReport.repository.TransactionRepository;
import com.uniquehire.TransactionAndReport.service.ExternalApiService;
import com.uniquehire.TransactionAndReport.service.TransactionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final ExternalApiService externalApiService;

    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  TransactionMapper transactionMapper,
                                  ExternalApiService externalApiService) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
        this.externalApiService = externalApiService;
    }

    @Override
    public TransactionResponseDto recordTransaction(TransactionRequestDto request) {
        try {
            System.out.println("=== recordTransaction started ===");
            System.out.println("Request paymentId: " + request.getPaymentId());
            System.out.println("Request amount: " + request.getAmount());
            System.out.println("Request gateway: " + request.getGateway());
            System.out.println("Request transactionStatus: " + request.getTransactionStatus());

            ExternalPaymentDto payment = externalApiService.getPaymentById(request.getPaymentId());
            System.out.println("Payment response: " + payment);

            if (payment == null) {
                throw new ResourceNotFoundException("Payment not found for paymentId: " + request.getPaymentId());
            }

            System.out.println("Loan ID from payment: " + payment.getLoanId());

            if (payment.getLoanId() == null) {
                throw new ValidationException("Loan ID not found in payment details for paymentId: " + request.getPaymentId());
            }

            ExternalLoanDto loan = externalApiService.getLoanById(payment.getLoanId());
            System.out.println("Loan response: " + loan);

            if (loan == null) {
                throw new ResourceNotFoundException("Loan not found for loanId: " + payment.getLoanId());
            }

            Transaction transaction = new Transaction();
            transaction.setPaymentId(request.getPaymentId());
            transaction.setLoanId(loan.getLoanId());
            transaction.setCustomerId(loan.getCustomerId());
            transaction.setAgentId(loan.getAgentId());
            transaction.setAmount(request.getAmount());
            transaction.setGateway(request.getGateway());
            transaction.setStatus(request.getTransactionStatus());
            transaction.setTimestamp(LocalDateTime.now());

            System.out.println("Transaction before save: " + transaction);

            Transaction saved = transactionRepository.save(transaction);
            System.out.println("Saved transaction: " + saved);

            TransactionResponseDto response = transactionMapper.toDto(saved);
            System.out.println("Mapped response DTO: " + response);

            return response;

        } catch (Exception e) {
            System.out.println("=== ERROR INSIDE recordTransaction ===");
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<TransactionResponseDto> getTransactionByPaymentId(Long paymentId) {
        return transactionRepository.findByPaymentId(paymentId)
                .stream()
                .map(transactionMapper::toDto)
                .collect(Collectors.toList());
    }
}