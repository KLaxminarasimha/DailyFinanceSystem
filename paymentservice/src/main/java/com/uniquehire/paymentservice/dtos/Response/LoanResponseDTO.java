package com.uniquehire.paymentservice.dtos.Response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class LoanResponseDTO {

    private Long loanId;
    private Long customerId;   // 🔥 ADD THIS

    private BigDecimal dailyEmi;
    private BigDecimal remainingAmount;
    private BigDecimal dueAmount;
    private BigDecimal fineAmount;
}