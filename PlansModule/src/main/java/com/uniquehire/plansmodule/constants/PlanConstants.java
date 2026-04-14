package com.uniquehire.plansmodule.constants;

public final class PlanConstants {

    private PlanConstants() {
    }

    public static final String PLAN_CREATED_SUCCESS = "Plan created successfully";
    public static final String PLANS_FETCHED_SUCCESS = "Plans fetched successfully";
    public static final String ELIGIBLE_PLANS_FETCHED_SUCCESS = "Eligible plans fetched successfully";

    public static final String PLAN_ALREADY_EXISTS = "Plan with this name already exists";
    public static final String INVALID_ADVANCE = "Advance must be less than total amount";
    public static final String INVALID_STATUS = "Invalid status. Allowed values: ACTIVE, INACTIVE";
    public static final String CUSTOMER_INCOME_NOT_FOUND = "Customer income not found";
    public static final String CUSTOMER_NOT_ELIGIBLE = "Customer is not eligible for any plan";
}