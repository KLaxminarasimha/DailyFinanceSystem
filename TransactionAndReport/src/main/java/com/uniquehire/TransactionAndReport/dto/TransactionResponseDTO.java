package com.uniquehire.TransactionAndReport.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionResponseDTO {

    private Long transactionId;

    private Long referenceId;
    private Long customerId;

    private BigDecimal amount;

    private String type;
    private String direction;

    private String status;

    private LocalDateTime timestamp;
}