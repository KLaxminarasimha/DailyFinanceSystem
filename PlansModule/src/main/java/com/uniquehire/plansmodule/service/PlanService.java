package com.uniquehire.plansmodule.service;

import com.uniquehire.plansmodule.dto.PlanResponse;

import java.util.List;

public interface PlanService {

    List<PlanResponse> getEligiblePlans(Long customerId);
}