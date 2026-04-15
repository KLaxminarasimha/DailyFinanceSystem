package com.uniquehire.plansmodule.dto.response;

import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerIncomeResponse {
    private Long customerId;
    private BigDecimal income;
}