package com.dailyfinance.fund_service.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class FundRequestDTO {
    private BigDecimal amount;
    private Long referenceId;
}
