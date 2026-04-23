package com.uniquehire.paymentservice.entity;

import com.uniquehire.paymentservice.enums.PaymentMethod;
import com.uniquehire.paymentservice.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @Column(nullable = false)
    private Long loanId;

    @Column(nullable = false)
    private LocalDate paymentDate;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal emiAmount;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal paidAmount;

    @Column(nullable = false,precision = 10,scale = 2)
    private BigDecimal dueAmount;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal fineAmount;

    @Column(nullable = false)
    private int daysCovered;

    @Column(nullable = false)
    private LocalDate nextEmiDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

   private String upiId;

    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL)
    private List<Fine> fines =new ArrayList<>();
}
