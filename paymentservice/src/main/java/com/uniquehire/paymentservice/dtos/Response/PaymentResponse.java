package com.uniquehire.paymentservice.dtos.Response;

import com.uniquehire.paymentservice.enums.PaymentMethod;
import com.uniquehire.paymentservice.enums.PaymentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {

    private Long paymentId;
    private Long loanId;

//    private LocalDate paymentDate;
    private BigDecimal emiAmount;
    private BigDecimal paidAmount;

    private BigDecimal dueAmount;
    private BigDecimal fineAmount;

    private int daysCovered;// if he pay extra amount then it will count days
    private LocalDate nextEmiDate;

    private PaymentStatus status;

    private  LocalDate paymentDate;

//    public static Object builder() {
//    }
}
