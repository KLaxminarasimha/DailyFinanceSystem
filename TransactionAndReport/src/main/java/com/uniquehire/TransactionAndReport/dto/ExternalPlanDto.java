package com.uniquehire.TransactionAndReport.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExternalPlanDto {
    private Long planId;
    private String name;
}
