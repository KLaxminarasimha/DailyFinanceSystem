package com.uniquehire.paymentservice.dtos.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayDueRequest {
    private Long paymentId;

    private BigDecimal amountPaid;
}
