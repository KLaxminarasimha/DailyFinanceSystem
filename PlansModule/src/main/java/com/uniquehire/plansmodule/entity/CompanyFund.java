package com.uniquehire.plansmodule.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "company_fund")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyFund {

    @Id
    private Long id = 1L;  // always single row

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal balance;
}