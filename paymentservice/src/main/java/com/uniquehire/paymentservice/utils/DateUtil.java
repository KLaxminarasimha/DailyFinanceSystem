package com.uniquehire.paymentservice.utils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public final class DateUtil {
    private DateUtil() {
    }

    public static long getDaysDifference(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }
}
