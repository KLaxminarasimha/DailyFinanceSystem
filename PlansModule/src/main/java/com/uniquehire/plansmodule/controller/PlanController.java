package com.uniquehire.plansmodule.controller;

import com.uniquehire.plansmodule.dto.PlanResponse;
import com.uniquehire.plansmodule.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plans")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;

    @GetMapping("/eligible/{customerId}")
    public List<PlanResponse> getEligiblePlans(@PathVariable Long customerId) {
        return planService.getEligiblePlans(customerId);
    }
}