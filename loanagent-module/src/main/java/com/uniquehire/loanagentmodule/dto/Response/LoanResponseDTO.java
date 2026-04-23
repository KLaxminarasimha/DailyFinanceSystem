package com.uniquehire.loanagentmodule.dto.Response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.uniquehire.loanagentmodule.enums.LoanStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@JsonPropertyOrder({
        "loanId",
        "customerName",
        "planName",
        "totalAmount",
        "advance",
        "givenAmount",
        "dailyEmi",
        "days",
        "startDate",
        "endDate",
        "status"
})
public class LoanResponseDTO {

    private Long loanId;
    private String customerName;
    private String planName;
    private BigDecimal totalAmount;
    private BigDecimal advance;
    private BigDecimal givenAmount;
    private BigDecimal dailyEmi;
    private Integer days;
    private LocalDate startDate;
    private LocalDate endDate;
    private LoanStatus status;

}
