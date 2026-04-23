package com.uniquehire.paymentservice.dtos.Request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayDueRequest {

//    @NotNull(message = "Payment ID required")
//    private Long paymentId;

    @NotNull(message = "loan Id requied ")
    private Long loanId;

    @NotNull(message = "Amount required")
    @DecimalMin(value = "1.0", message = "Amount must be > 0")
    private BigDecimal amountPaid;
}
