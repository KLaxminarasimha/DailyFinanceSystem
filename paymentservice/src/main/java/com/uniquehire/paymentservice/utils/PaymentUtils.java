//package com.uniquehire.paymentservice.utils;
//
//import java.math.BigDecimal;
//
//public class PaymentUtils {
//    //emi=1% of total loan EMI = totalLoanAmount × 0.01
//    public static BigDecimal calculateEmi(BigDecimal totalLoanAmount) {
//        return totalLoanAmount.multiply(new BigDecimal("0.01"));
//    }
//
//    //Fine = EMI × 0.01
//    public static BigDecimal calculateFine(BigDecimal emi) {
//        return emi.multiply(new BigDecimal("0.01"));
//    }
//
//    //days covered for extra payments(intValue()(removes decimal)
//    public static int calculateDaysCovered(BigDecimal paidAmount, BigDecimal emi) {
//        return paidAmount.divide(emi).intValue();
//    }
//
//    //Remaining (extra leftover)
//    public static BigDecimal calculateRemaining(BigDecimal paidAmount, BigDecimal emi) {
//        return paidAmount.remainder(emi);
//    }
//
//    //  Due calculation(New Due = (Current EMI + Previous Due) − Paid Amount)
//    public static BigDecimal calculateDue(BigDecimal emi, BigDecimal oldDue, BigDecimal paidAmount) {
//        return emi.add(oldDue).subtract(paidAmount);
//    }
//
//
//}
