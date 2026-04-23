package com.uniquehire.paymentservice.dtos.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

    private Long loanId;
    private BigDecimal payAmount;
    private String paymentMethod;

    private String upiId;
    private String phoneNumber;
    private String email;
}
