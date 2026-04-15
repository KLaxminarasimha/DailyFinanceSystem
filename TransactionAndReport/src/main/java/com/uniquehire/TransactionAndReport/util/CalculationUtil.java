package com.uniquehire.TransactionAndReport.util;

import java.math.BigDecimal;

public class CalculationUtil {

    public static double calculatePercentage(BigDecimal paid, BigDecimal total) {

        if (total == null || total.doubleValue() == 0) {
            return 0;
        }

        return (paid.doubleValue() / total.doubleValue()) * 100;
    }

    public static BigDecimal calculateCommission(BigDecimal target, double rate) {
        return target.multiply(BigDecimal.valueOf(rate));
    }
}
