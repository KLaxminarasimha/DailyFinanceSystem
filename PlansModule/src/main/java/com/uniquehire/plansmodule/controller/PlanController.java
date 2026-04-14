package com.uniquehire.plansmodule.controller;


import com.uniquehire.plansmodule.constants.PlanConstants;
import com.uniquehire.plansmodule.dto.request.CreatePlanRequest;
import com.uniquehire.plansmodule.dto.response.ApiResponse;
import com.uniquehire.plansmodule.dto.response.EligibilityResponse;
import com.uniquehire.plansmodule.dto.response.PlanResponse;
import com.uniquehire.plansmodule.service.PlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plans")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;

    @PostMapping
    public ResponseEntity<ApiResponse<PlanResponse>> createPlan(@Valid @RequestBody CreatePlanRequest request) {
        PlanResponse response = planService.createPlan(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<PlanResponse>builder()
                        .success(true)
                        .message(PlanConstants.PLAN_CREATED_SUCCESS)
                        .data(response)
                        .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PlanResponse>>> getAllPlans(
            @RequestParam(required = false) String status) {
        List<PlanResponse> response = planService.getAllPlans(status);
        return ResponseEntity.ok(
                ApiResponse.<List<PlanResponse>>builder()
                        .success(true)
                        .message(PlanConstants.PLANS_FETCHED_SUCCESS)
                        .data(response)
                        .build()
        );
    }

    @GetMapping("/eligible/{customerId}")
    public ResponseEntity<ApiResponse<EligibilityResponse>> getEligiblePlans(@PathVariable Long customerId) {
        EligibilityResponse response = planService.getEligiblePlans(customerId);

        String message = response.getEligiblePlans().isEmpty()
                ? PlanConstants.CUSTOMER_NOT_ELIGIBLE
                : PlanConstants.ELIGIBLE_PLANS_FETCHED_SUCCESS;

        return ResponseEntity.ok(
                ApiResponse.<EligibilityResponse>builder()
                        .success(true)
                        .message(message)
                        .data(response)
                        .build()
        );
    }
}
