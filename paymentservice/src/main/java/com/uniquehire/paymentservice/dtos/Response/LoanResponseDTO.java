package com.uniquehire.paymentservice.dtos.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanResponseDTO {
    private Long loanId;
    private BigDecimal totalAmount;
}
