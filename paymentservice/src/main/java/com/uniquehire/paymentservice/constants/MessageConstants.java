package com.uniquehire.paymentservice.constants;


public class MessageConstants {

    // Loan
    public static final String LOAN_NOT_FOUND = "Loan not found";
    public static final String LOAN_CLOSED = "Loan is already closed";

    // Payment
    public static final String PAYMENT_SUCCESS = "Payment completed successfully";
    public static final String PAYMENT_LIST = "Payments retrieved successfully";
    public static final String INVALID_AMOUNT = "Amount must be greater than zero";

    // Due Payment
    public static final String DUE_PAYMENT_SUCCESS = "Due payment completed successfully";

    // OTP
    public static final String OTP_SENT = "OTP sent successfully";
    public static final String OTP_VERIFIED = "OTP verified successfully";
    public static final String OTP_INVALID = "Invalid OTP";
    public static final String OTP_EXPIRED = "OTP expired";

    // Fine
    public static final String FINE_CREATED = "Fine created successfully";
    public static final String FINE_LIST = "Fines retrieved successfully";
    public static final String FINE_UPDATED = "Fine updated successfully";
    public static final String INSUFFICIENT_FINE_PAYMENT = "Full fine amount must be paid";
}