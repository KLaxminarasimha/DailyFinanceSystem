//package com.uniquehire.plansmodule.enums;
//
//
//import lombok.Data;
//
//import java.math.BigDecimal;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//public enum EligibilitySlab {
//
//    NOT_ELIGIBLE(BigDecimal.ZERO, BigDecimal.valueOf(9999), Collections.emptyList()),
//    BRONZE_ONLY(BigDecimal.valueOf(10000), BigDecimal.valueOf(19999), List.of(PlanType.BRONZE)),
//    BRONZE_SILVER(BigDecimal.valueOf(20000), BigDecimal.valueOf(29999), List.of(PlanType.BRONZE, PlanType.SILVER)),
//    BRONZE_SILVER_GOLD(BigDecimal.valueOf(30000), BigDecimal.valueOf(49999), List.of(PlanType.BRONZE, PlanType.SILVER, PlanType.GOLD)),
//    BRONZE_SILVER_GOLD_PLATINUM(BigDecimal.valueOf(50000), BigDecimal.valueOf(74999), List.of(PlanType.BRONZE, PlanType.SILVER, PlanType.GOLD, PlanType.PLATINUM)),
//    ALL_PLANS(BigDecimal.valueOf(75000), null, List.of(PlanType.BRONZE, PlanType.SILVER, PlanType.GOLD, PlanType.PLATINUM, PlanType.DIAMOND));
//
//    private final BigDecimal minIncome;
//    private final BigDecimal maxIncome;
//    private final List<PlanType> eligiblePlans;
//
//    EligibilitySlab(BigDecimal minIncome, BigDecimal maxIncome, List<PlanType> eligiblePlans) {
//        this.minIncome = minIncome;
//        this.maxIncome = maxIncome;
//        this.eligiblePlans = eligiblePlans;
//    }
//
//    public static List<PlanType> getEligiblePlans(BigDecimal income) {
//        if (income == null) {
//            return Collections.emptyList();
//        }
//
//        return Arrays.stream(values())
//                .filter(slab -> slab.matches(income))
//                .findFirst()
//                .map(EligibilitySlab::getEligiblePlans)
//                .orElse(Collections.emptyList());
//    }
//
//    public boolean matches(BigDecimal income) {
//        boolean minMatch = income.compareTo(minIncome) >= 0;
//        boolean maxMatch = maxIncome == null || income.compareTo(maxIncome) <= 0;
//        return minMatch && maxMatch;
//    }
//
//    public List<PlanType> getEligiblePlans() {
//        return eligiblePlans;
//    }
//}