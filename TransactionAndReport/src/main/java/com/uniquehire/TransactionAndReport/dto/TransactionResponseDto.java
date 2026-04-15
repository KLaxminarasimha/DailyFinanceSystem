package com.uniquehire.TransactionAndReport.dto;

import com.uniquehire.TransactionAndReport.enums.PaymentGateway;
import com.uniquehire.TransactionAndReport.enums.TransactionStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponseDto {
    @NotNull
    private Long transactionId;

    @NotNull
    private Long paymentId;

    private Long loanId;
    private Long customerId;
    private Long agentId;

    @NotNull
    @DecimalMin(value="0.0", inclusive= false)
    private BigDecimal amount;

    @NotNull
    private PaymentGateway gateway;

    @NotNull
    private TransactionStatus status;

    @NotNull
    private LocalDateTime timestamp;
}
