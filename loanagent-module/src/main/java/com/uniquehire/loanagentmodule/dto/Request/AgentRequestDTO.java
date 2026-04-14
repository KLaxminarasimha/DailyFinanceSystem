package com.uniquehire.loanagentmodule.dto.Request;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AgentRequestDTO {

    @NotBlank
    private String name;

    @Pattern(regexp="^[6-9]\\d{9}$")
    private String phone;

    @Email
    private String email;

    private String area;

    @DecimalMin("0.0")
    private BigDecimal collectionTarget;

    @DecimalMin("0.0")
    private BigDecimal commissionRate;
}
