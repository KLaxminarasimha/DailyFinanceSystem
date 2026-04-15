package com.uniquehire.plansmodule.service;



import com.uniquehire.plansmodule.dto.request.CreatePlanRequest;
import com.uniquehire.plansmodule.dto.response.EligibilityResponse;
import com.uniquehire.plansmodule.dto.response.PlanResponse;

import java.util.List;

public interface PlanService {

    PlanResponse createPlan(CreatePlanRequest request);

    List<PlanResponse> getAllPlans(String status);

    EligibilityResponse getEligiblePlans(Long customerId);
}
