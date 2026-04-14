package com.uniquehire.loanagentmodule.dto.Request;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class LoanRequestDTO {

    @NotNull
    private Long customerId;

    @NotNull
    private Long planId;

    @NotNull
    private Long agentId;

    @NotNull
    private BigDecimal totalAmount;

    @NotNull
    private BigDecimal advance;

    @NotNull
    private BigDecimal dailyEmi;

    @NotNull
    private Integer days;

  }
