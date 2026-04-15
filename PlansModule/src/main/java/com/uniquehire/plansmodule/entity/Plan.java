package com.uniquehire.plansmodule.entity;


import com.uniquehire.plansmodule.enums.PlanStatus;
import com.uniquehire.plansmodule.enums.PlanType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "plans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_id")
    private Long planId;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false, unique = true, length = 50)
    private PlanType name;

    @Column(name = "total_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "advance", nullable = false, precision = 12, scale = 2)
    private BigDecimal advance;

    @Column(name = "daily_emi", nullable = false, precision = 10, scale = 2)
    private BigDecimal dailyEmi;

    @Column(name = "days", nullable = false)
    private Integer days;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PlanStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void setDefaults() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (status == null) {
            status = PlanStatus.ACTIVE;
        }
    }
}
