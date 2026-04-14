package com.uniquehire.loanagentmodule.dto.Response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class AgentResponseDTO {

    private Long agentId;
    private String name;
    private String phone;
    private String email;
    private String area;
    private Integer assignedLoans;
    private BigDecimal collectionTarget;
    private BigDecimal commissionRate;

}