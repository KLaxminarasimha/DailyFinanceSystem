package com.uniquehire.plansmodule.entity;

import com.uniquehire.plansmodule.enums.PlanStatus;
import com.uniquehire.plansmodule.enums.PlanType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "plans")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long planId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private PlanType name;

    // ✅ Base value only
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal planAmount;

    @Column(nullable = false)
    private Integer durationDays;

    @Enumerated(EnumType.STRING)
    private PlanStatus status;

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