package com.uniquehire.TransactionAndReport.dto.External;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExternalPaymentDto {
    private Long paymentId;
    private Long loanId;
    private Long customerId;
    private String customerName;
    private LocalDate paymentDate;
    private BigDecimal emiAmount;
    private BigDecimal paidAmount;
    private BigDecimal fine;
    private BigDecimal totalPaid;
    private String status;
    private String paymentMethod;
    private String referenceId;
}