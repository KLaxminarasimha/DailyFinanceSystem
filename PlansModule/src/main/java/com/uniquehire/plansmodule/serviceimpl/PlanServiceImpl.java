package com.uniquehire.plansmodule.serviceimpl;



import com.uniquehire.plansmodule.client.CustomerClient;
import com.uniquehire.plansmodule.constants.PlanConstants;
import com.uniquehire.plansmodule.dto.request.CreatePlanRequest;
import com.uniquehire.plansmodule.dto.response.CustomerIncomeResponse;
import com.uniquehire.plansmodule.dto.response.EligibilityResponse;
import com.uniquehire.plansmodule.dto.response.PlanResponse;
import com.uniquehire.plansmodule.entity.Plan;
import com.uniquehire.plansmodule.enums.PlanStatus;
import com.uniquehire.plansmodule.enums.PlanType;
import com.uniquehire.plansmodule.exception.BadRequestException;
import com.uniquehire.plansmodule.exception.DuplicateResourceException;
import com.uniquehire.plansmodule.repository.PlanRepository;
import com.uniquehire.plansmodule.service.PlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;
    private final CustomerClient customerClient;
    private final RestTemplate restTemplate;



    @Override
    public PlanResponse createPlan(CreatePlanRequest request) {

        if (planRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException(PlanConstants.PLAN_ALREADY_EXISTS);
        }

        if (request.getAdvance().compareTo(request.getTotalAmount()) >= 0) {
            throw new BadRequestException(PlanConstants.INVALID_ADVANCE);
        }

        Plan plan = Plan.builder()
                .name(request.getName())
                .totalAmount(request.getTotalAmount())
                .givenAmount(request.getGivenAmount())
                .advance(request.getAdvance())
                .dailyEmi(request.getDailyEmi())
                .days(request.getDays())
                .status(request.getStatus())
                .build();

        Plan savedPlan = planRepository.save(plan);

        log.info("Plan created successfully with id: {}", savedPlan.getPlanId());

        return mapToResponse(savedPlan);
    }

    @Override
    public List<PlanResponse> getAllPlans(String status) {

        List<Plan> plans;

        if (status == null || status.isBlank()) {
            plans = planRepository.findAll();
        } else {
            PlanStatus planStatus;
            try {
                planStatus = PlanStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException ex) {
                throw new BadRequestException(PlanConstants.INVALID_STATUS);
            }
            plans = planRepository.findByStatus(planStatus);
        }

        plans.sort(Comparator.comparing(Plan::getTotalAmount));

        List<PlanResponse> responseList = new ArrayList<>();
        for (Plan plan : plans) {
            responseList.add(mapToResponse(plan));
        }

        return responseList;
    }

    @Override
    public EligibilityResponse getEligiblePlans(Long customerId) {
//        CustomerIncomeResponse incomeResponse= restTemplate.getForObject(""+customerId,CustomerIncomeResponse.class);

//        BigDecimal income = incomeResponse.getIncome();

        BigDecimal income = BigDecimal.valueOf(9999);

        List<PlanType> eligiblePlanTypes = getEligiblePlanTypes(income);

        List<PlanResponse> eligiblePlans = new ArrayList<>();

        if (!eligiblePlanTypes.isEmpty()) {
            List<Plan> activePlans = planRepository.findByStatus(PlanStatus.ACTIVE);
            activePlans.sort(Comparator.comparing(Plan::getTotalAmount));

            for (Plan plan : activePlans) {
                if (eligiblePlanTypes.contains(plan.getName())) {
                    eligiblePlans.add(mapToResponse(plan));
                }
            }
        }

        log.info("Eligibility checked for customerId: {}, income: {}, eligible plans count: {}",
                customerId, income, eligiblePlans.size());

        return EligibilityResponse.builder()
                .customerId(customerId)
                .income(income)
                .eligiblePlans(eligiblePlans)
                .build();
    }

    private List<PlanType> getEligiblePlanTypes(BigDecimal income) {

        if (income == null || income.compareTo(BigDecimal.valueOf(10000)) < 0) {
            return Collections.emptyList();
        }

        if (income.compareTo(BigDecimal.valueOf(10000)) >= 0
                && income.compareTo(BigDecimal.valueOf(20000)) < 0) {
            return List.of(PlanType.BRONZE);
        }

        if (income.compareTo(BigDecimal.valueOf(20000)) >= 0
                && income.compareTo(BigDecimal.valueOf(30000)) < 0) {
            return List.of(PlanType.BRONZE, PlanType.SILVER);
        }

        if (income.compareTo(BigDecimal.valueOf(30000)) >= 0
                && income.compareTo(BigDecimal.valueOf(50000)) < 0) {
            return List.of(PlanType.BRONZE, PlanType.SILVER, PlanType.GOLD);
        }

        if (income.compareTo(BigDecimal.valueOf(50000)) >= 0
                && income.compareTo(BigDecimal.valueOf(75000)) < 0) {
            return List.of(PlanType.BRONZE, PlanType.SILVER, PlanType.GOLD, PlanType.PLATINUM);
        }

        return List.of(PlanType.BRONZE, PlanType.SILVER, PlanType.GOLD, PlanType.PLATINUM, PlanType.DIAMOND);
    }

    private PlanResponse mapToResponse(Plan plan) {
        return PlanResponse.builder()
                .planId(plan.getPlanId())
                .name(plan.getName().name())
                .totalAmount(plan.getTotalAmount())
                .givenAmount(plan.getGivenAmount())
                .advance(plan.getAdvance())
                .dailyEmi(plan.getDailyEmi())
                .days(plan.getDays())
                .status(plan.getStatus().name())
                .createdAt(plan.getCreatedAt())
                .build();
    }
}