package com.uniquehire.loanagentmodule.utils;

import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class LoanCalculator {

    public BigDecimal calculateGivenAmount(BigDecimal total,BigDecimal advance){

        return total.subtract(advance);

    }
    public LocalDate calculateEndDate(LocalDate start,Integer days){

        return start.plusDays(days);

    }
}
