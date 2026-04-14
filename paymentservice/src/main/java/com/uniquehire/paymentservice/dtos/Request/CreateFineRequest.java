package com.uniquehire.paymentservice.dtos.Request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateFineRequest {

    @NotNull(message = "Loan id is required")
    private Long loanId;

    @NotNull(message = "payment id is required")
    private Long paymentId;

    @NotNull(message = "Fine amount is required")
    private BigDecimal fineAmount;

    @Size(max = 255, message = "Reason must not exceed 255 characters")
    private String reason;


    @NotNull(message = "Fine date is required")
    private LocalDate fineDate;

}
