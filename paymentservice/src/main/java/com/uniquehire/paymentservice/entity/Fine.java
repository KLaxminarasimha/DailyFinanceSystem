package com.uniquehire.paymentservice.entity;

import com.uniquehire.paymentservice.enums.FineStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "fines")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Fine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fineId;

    @Column(nullable = false)
    private Long loanId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal fineAmount;

    @Column(length = 255)
    private String reason;

    @Column(nullable = false)
    private LocalDate fineDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FineStatus status;


}
