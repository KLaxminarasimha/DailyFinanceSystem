package com.uniquehire.paymentservice.constants;


public class MessageConstants {

    public static final String LOAN_NOT_FOUND = "Loan not found";
    public static final String LOAN_CLOSED = "Cannot make payment on a closed loan";
    public static final String INVALID_AMOUNT = "Paid amount must be greater than zero";
    public static final String PAYMENT_SUCCESS = "Payment recorded successfully";
    public static final String PAYMENT_LIST = "Payments retrieved successfully";

    public static final String FINE_CREATED = "Fine recorded successfully";
    public static final String FINE_LIST = "Fines retrieved successfully";
    public static final String FINE_UPDATED = "Fine status updated successfully";

    public static final String INSUFFICIENT_FINE_PAYMENT = "Please pay full fine amount";
}