package com.uniquehire.paymentservice.dtos.Request;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class PaymentRequestDTO {

    private Long loanId;
    private BigDecimal amount;


}