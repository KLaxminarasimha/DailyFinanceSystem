package com.uniquehire.paymentservice.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PaymentCalculationUtil {

    // EMI = 1% of total loan
    public static BigDecimal calculateEmi(BigDecimal loanAmount) {
        return loanAmount.multiply(BigDecimal.valueOf(0.01));
    }

    // Fine = 1% of EMI
    public static BigDecimal calculateFine(BigDecimal emi) {
        return emi.multiply(BigDecimal.valueOf(0.01));
    }

    // Due calculation
    public static BigDecimal calculateDue(BigDecimal emi, BigDecimal paid, BigDecimal oldDue) {
        return emi.add(oldDue).subtract(paid);
    }

    // Days covered
    public static int calculateDays(BigDecimal paid, BigDecimal emi) {
        return paid.divide(emi, 0, RoundingMode.DOWN).intValue();
    }
}