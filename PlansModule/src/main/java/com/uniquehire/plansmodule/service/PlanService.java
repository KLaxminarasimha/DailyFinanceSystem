package com.uniquehire.plansmodule.service;

import com.uniquehire.plansmodule.dto.PlanResponse;
import java.util.List;

public interface PlanService {

    PlanResponse getPlanById(Long id);

    List<PlanResponse> getEligiblePlans(Long userId); // 🔥 important
}