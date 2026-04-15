package com.uniquehire.TransactionAndReport.dto;

import com.uniquehire.TransactionAndReport.constants.MessageConstants;
import com.uniquehire.TransactionAndReport.enums.PaymentGateway;
import com.uniquehire.TransactionAndReport.enums.TransactionStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestDto {
    @NotNull(message= MessageConstants.PAYMENT_ID_REQUIRED)
    private Long paymentId;

    @NotNull(message= MessageConstants.AMOUNT_REQUIRED)
    @DecimalMin(value = "0.0", inclusive = false, message = MessageConstants.AMOUNT_REQUIRED)
    private BigDecimal amount;

    @NotNull(message = MessageConstants.GATEWAY_REQUIRED)
    private PaymentGateway gateway;

    @NotNull(message = MessageConstants.STATUS_REQUIRED)
    private TransactionStatus transactionStatus;
}
