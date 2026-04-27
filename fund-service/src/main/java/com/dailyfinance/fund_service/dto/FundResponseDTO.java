package com.dailyfinance.fund_service.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class FundResponseDTO {

    private Long id;
    private BigDecimal balance;
}