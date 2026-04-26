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

    // 🔓 PUBLIC
    @GetMapping("/{id}")
    public PlanResponse getPlan(@PathVariable Long id) {
        return planService.getPlanById(id);
    }

    // 🔐 SECURE (NO customerId)
    @GetMapping("/eligible")
    public List<PlanResponse> getEligiblePlans(
            @RequestHeader("X-USER-ID") Long userId) {

        return planService.getEligiblePlans(userId);
    }
}