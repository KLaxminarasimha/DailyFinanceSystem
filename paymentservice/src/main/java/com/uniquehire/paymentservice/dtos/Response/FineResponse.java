package com.uniquehire.paymentservice.dtos.Response;

import com.uniquehire.paymentservice.enums.FineStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FineResponse{

    private Long fineId;
    private Long loanId;
    private Long paymentId;
    private BigDecimal fineAmount;
    private String reason;
    private LocalDate fineDate;
    private FineStatus status;
//    private LocalDateTime createdAt;
}
