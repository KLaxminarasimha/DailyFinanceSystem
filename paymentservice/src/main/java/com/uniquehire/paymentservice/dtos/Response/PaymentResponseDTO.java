package com.uniquehire.paymentservice.dtos.Response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentResponseDTO {

    private Long paymentId;
    private Long loanId;
    private Long customerId;

    private BigDecimal amount;

    private String type;
    private String status;

    private LocalDateTime paymentDate;
}