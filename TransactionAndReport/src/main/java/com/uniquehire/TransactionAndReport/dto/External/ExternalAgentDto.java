package com.uniquehire.TransactionAndReport.dto.External;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExternalAgentDto {
    private Long agentId;
    private String name;
    private String phone;
    private String email;
    private String area;
    private Integer assignedLoans;
    private BigDecimal collectionTarget;
    private BigDecimal commissionRate;
}
