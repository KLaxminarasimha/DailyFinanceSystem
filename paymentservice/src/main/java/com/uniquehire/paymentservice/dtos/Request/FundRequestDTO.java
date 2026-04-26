package com.uniquehire.paymentservice.dtos.Request;


import lombok.Data;
import java.math.BigDecimal;

@Data
public class FundRequestDTO {

    private BigDecimal amount;
    private Long referenceId;
}
