package com.uniquehire.paymentservice.dtos.Request;

import com.uniquehire.paymentservice.enums.PaymentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePaymentStatusRequest {

    private Long paymentId;

    @NotNull(message = "Payment status is required")
    private PaymentStatus status;


}
