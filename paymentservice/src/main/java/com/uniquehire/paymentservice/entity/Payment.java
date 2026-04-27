package com.uniquehire.paymentservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    private Long loanId;
    private Long customerId;

    private BigDecimal amount;

    private String type; // EMI / PENALTY
    private String status; // SUCCESS / FAILED

    private LocalDateTime paymentDate;
}