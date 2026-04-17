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
    private LocalDate paymentDate;
    private BigDecimal emiAmount;
    private BigDecimal paidAmount;
    private BigDecimal fine;
    private BigDecimal totalPaid;// because contract response shows it.
    private PaymentStatus status;
    private PaymentMethod paymentMethod;
    private String referenceId;
    private BigDecimal enteredAmount;   // user entered
    private Long overdueDays;           // late days
    private String upiLink;             // UPI link
//    private LocalDateTime createdAt;

//    private List<FineResponse> fines;
}
