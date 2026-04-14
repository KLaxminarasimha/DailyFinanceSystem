package com.uniquehire.paymentservice.dtos.Request;

import com.uniquehire.paymentservice.enums.PaymentMethod;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;


import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentRequest {
    @NotNull(message = "Paid amount is required")
    private Long loanId;

    @NotNull(message = "Paid amount is required")
    private BigDecimal paidAmount;

    @NotNull(message = "Payment date is required")
    private LocalDate paymentDate;

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;

    @Size(max = 50, message = "Reference id must not exceed 50 characters")
    private String referenceId;
}
