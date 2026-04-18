package com.uniquehire.TransactionAndReport.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExternalCustomerDto {
    private Long customerId;
    private String name;
}
