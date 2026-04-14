package com.uniquehire.paymentservice.utils;

import com.uniquehire.paymentservice.constants.AppConstants;

import java.math.BigDecimal;

public final class FineCalculatorUtil {

    private FineCalculatorUtil() {
    }

    public static BigDecimal calculateFine(long overdueDays) {
        if (overdueDays <= 0) {
            return BigDecimal.ZERO;
        }
        return AppConstants.FINE_PER_DAY.multiply(BigDecimal.valueOf(overdueDays));
    }
}
