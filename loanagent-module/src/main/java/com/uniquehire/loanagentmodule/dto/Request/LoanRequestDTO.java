package com.uniquehire.loanagentmodule.dto.Request;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class LoanRequestDTO {


    @NotNull
    private Long planId;


  }
