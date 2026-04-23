package com.uniquehire.paymentservice.dtos.Request;

import com.uniquehire.paymentservice.enums.PaymentMethod;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {


    @NotNull(message = "Pay amount is required")
    @DecimalMin(value = "1.0", message = "Amount must be greater than 0")
    private BigDecimal paidAmount;

    @NotBlank(message = "Payment method required")
    private PaymentMethod paymentMethod;

    @NotBlank(message = "UPI ID required")
    private String upiId;

    private LocalDate paymentDate;

    private String email;

}
