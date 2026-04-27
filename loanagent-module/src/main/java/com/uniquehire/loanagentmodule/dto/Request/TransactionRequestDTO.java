package com.uniquehire.loanagentmodule.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TransactionRequestDTO {

    private Long referenceId;
    private Long customerId;
    private BigDecimal amount;
    private String type;
    private String direction;
}
