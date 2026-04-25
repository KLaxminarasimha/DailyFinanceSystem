package com.uniquehire.plansmodule.serviceimpl;

import com.uniquehire.plansmodule.dto.CustomerResponse;
import com.uniquehire.plansmodule.dto.PlanResponse;
import com.uniquehire.plansmodule.entity.CompanyFund;
import com.uniquehire.plansmodule.entity.Plan;
import com.uniquehire.plansmodule.enums.PlanStatus;
import com.uniquehire.plansmodule.repository.CompanyFundRepository;
import com.uniquehire.plansmodule.repository.PlanRepository;
import com.uniquehire.plansmodule.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;
    private final CompanyFundRepository fundRepository;
    private final RestTemplate restTemplate;

    @Override
    public List<PlanResponse> getEligiblePlans(Long customerId) {

        // 🔹 1. Call customer-service
        String url = "http://customer-service/customers/" + customerId;

        CustomerResponse customer =
                restTemplate.getForObject(url, CustomerResponse.class);

        BigDecimal income = getIncome(customer);
        System.out.println("Income = " + income);

        if (income == null) {
            throw new RuntimeException("Income is null");
        }

        // 🔹 2. Get company fund
        CompanyFund fund = fundRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Fund not found"));

        BigDecimal fundBalance = fund.getBalance();

        // 🔹 3. Get ACTIVE plans
        List<Plan> plans = planRepository.findByStatus(PlanStatus.ACTIVE);

        // 🔹 4. Map to response
        return plans.stream()
                .filter(p -> p.getPlanAmount().compareTo(income) <= 0)
                .map(p -> mapToResponse(p, fundBalance))
                .toList();
    }

    // 🔥 Extract income
    private BigDecimal getIncome(CustomerResponse customer) {

        if (customer == null) {
            throw new RuntimeException("Customer is null");
        }

        if ("EMPLOYEE".equalsIgnoreCase(customer.getUserType())) {

            if (customer.getEmployeeDetails() != null &&
                    customer.getEmployeeDetails().getMonthlySalary() != null) {

                return customer.getEmployeeDetails().getMonthlySalary();
            }

        } else if ("BUSINESS".equalsIgnoreCase(customer.getUserType())) {

            if (customer.getBusinessDetails() != null &&
                    customer.getBusinessDetails().getMonthlyIncome() != null) {

                return customer.getBusinessDetails().getMonthlyIncome();
            }
        }

        return BigDecimal.ZERO;
    }
    @Override
    public PlanResponse getPlanById(Long planId) {

        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        BigDecimal planAmount = plan.getPlanAmount();

        BigDecimal disbursed = planAmount.multiply(BigDecimal.valueOf(0.9));
        BigDecimal interest = planAmount.multiply(BigDecimal.valueOf(0.1));
        BigDecimal dailyEmi = planAmount.multiply(BigDecimal.valueOf(0.01));

        PlanResponse response = new PlanResponse();

        response.setPlanId(plan.getPlanId());
        response.setPlanAmount(planAmount);
        response.setDisbursedAmount(disbursed);
        response.setInterestAmount(interest);
        response.setTotalPayable(planAmount);
        response.setDailyEmi(dailyEmi);
        response.setDuration(plan.getDurationDays()); // 👈 IMPORTANT
        response.setStatus(plan.getStatus().name());

        return response;
    }

    // 🔥 Mapping logic
    private PlanResponse mapToResponse(Plan plan, BigDecimal fundBalance) {

        BigDecimal planAmount = plan.getPlanAmount();

        BigDecimal disbursed = planAmount.multiply(BigDecimal.valueOf(0.9));
        BigDecimal interest = planAmount.multiply(BigDecimal.valueOf(0.1));
        BigDecimal dailyEmi = planAmount.multiply(BigDecimal.valueOf(0.01));

        PlanResponse response = new PlanResponse();

        response.setPlanId(plan.getPlanId());
        response.setPlanAmount(planAmount);
        response.setDisbursedAmount(disbursed);
        response.setInterestAmount(interest);
        response.setTotalPayable(planAmount);
        response.setDailyEmi(dailyEmi);
        response.setDuration(plan.getDurationDays());

        // 🔥 ACTIVE / INACTIVE logic
        if (planAmount.compareTo(fundBalance) <= 0) {
            response.setStatus("ACTIVE");
        } else {
            response.setStatus("INACTIVE");
        }

        return response;
    }
}