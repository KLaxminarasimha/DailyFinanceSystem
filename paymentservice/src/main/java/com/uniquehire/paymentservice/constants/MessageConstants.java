package com.uniquehire.paymentservice.constants;

public final class MessageConstants {

    private MessageConstants() {
    }
    public static final String PAYMENT_RECORDED_SUCCESS="Payment recorded successfully";
    public static final String PAYMENTS_FETCHED_SUCCESS="Payments retrieved successfully ";
    public static final String PAYMENT_STATUS_UPDATED_SUCCESS="Payment status updated successfully";

    public static final String FINE_RECORDED_SUCCESS="Fine recorded successfully";
    public static final String FINES_FETCHED_SUCCESS="Fines retrieved successfully ";
    public static final String FINE_STATUS_UPDATED_SUCCESS="Fine status updated successfully";

    public static final String PAYMENT_NOT_FOUND="payment not found";
    public static final String FINE_NOT_FOUND="Fine not found";
    public static final String INVALID_PAYMENT_DATE="Payment date must be less than or equal to today";
    public static final String INVALID_FINE_DATE="Fine date must be less than or equal to today";
    public static final String INVALID_PAID_AMOUNT="Paid amount must be greater than zero";
    public static final String INVALID_FINE_AMOUNT="Fine amount must be greater than zero";
    public static final String INVALID_LOAN_ID = "Loan id is required";
    public static final String LOAN_CLOSED ="Cannot make payment on a closed loan";
    public static final String PAYMENT_EXCEEDS_EMI = "Payment amount exceeds outstanding EMI";
    public static final String PAYMENT_DELETED_SUCCESS = "Payment deleted successfully";
    public static final String FINE_DELETED_SUCCESS = "Fine deleted successfully";
    public static final String ALL_PAYMENTS_FETCHED_SUCCESS = "All payments fetched successfully";
    public static final String ALL_FINES_FETCHED_SUCCESS = "All fines fetched successfully";
}

