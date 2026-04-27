package com.dailyfinance.fund_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class Fund {

    @Id
    private Long id = 1L; // single record

    private BigDecimal balance;
}