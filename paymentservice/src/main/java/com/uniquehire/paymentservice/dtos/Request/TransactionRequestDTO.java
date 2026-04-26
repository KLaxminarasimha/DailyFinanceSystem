package com.uniquehire.paymentservice.dtos.Request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TransactionRequestDTO {

    private Long referenceId; // loanId
    private Long customerId;
    private BigDecimal amount;
    private String type;      // EMI / PARTIAL / ADVANCE
    private String direction; // CREDIT / DEBIT
}