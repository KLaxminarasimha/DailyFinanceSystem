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

    // 🔥 SECURE METHOD (USES userId)
    @Override
    public List<PlanResponse> getEligiblePlans(Long userId) {

        // 🔥 CALL CUSTOMER SERVICE USING userId
        String url = "http://customer-service/customers/user/" + userId;

        CustomerResponse customer =
                restTemplate.getForObject(url, CustomerResponse.class);

        BigDecimal income = getIncome(customer);
        System.out.println("FULL CUSTOMER RESPONSE = " + customer);
        System.out.println("EMPLOYEE DETAILS = " + customer.getEmployeeDetails());

        if (income == null || income.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Invalid income");
        }

        // 🔹 FUND CHECK
        CompanyFund fund = fundRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Fund not found"));

        BigDecimal fundBalance = fund.getBalance();

        // 🔹 GET ACTIVE PLANS
        List<Plan> plans = planRepository.findByStatus(PlanStatus.ACTIVE);


        return plans.stream()
                .filter(p -> p.getPlanAmount().compareTo(income) <= 0)
                .map(p -> mapToResponse(p, fundBalance))
                .toList();
    }

    // 🔥 GET PLAN BY ID
    @Override
    public PlanResponse getPlanById(Long planId) {

        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        return mapToResponse(plan, BigDecimal.ZERO);
    }

    // 🔥 INCOME LOGIC
    private BigDecimal getIncome(CustomerResponse customer) {

        if (customer == null) {
            throw new RuntimeException("Customer not found");
        }

        // 🔹 EMPLOYEE
        if ("EMPLOYEE".equalsIgnoreCase(customer.getUserType())) {

            if (customer.getEmployeeDetails() == null ||
                    customer.getEmployeeDetails().getMonthlySalary() == null) {

                throw new RuntimeException("Employee details not completed");
            }

            return customer.getEmployeeDetails().getMonthlySalary();
        }

        // 🔹 BUSINESS
        if ("BUSINESS".equalsIgnoreCase(customer.getUserType())) {

            if (customer.getBusinessDetails() == null ||
                    customer.getBusinessDetails().getMonthlyIncome() == null) {

                throw new RuntimeException("Business details not completed");
            }

            return customer.getBusinessDetails().getMonthlyIncome();
        }

        throw new RuntimeException("Invalid user type");
    }

    // 🔥 COMMON MAPPING
    private PlanResponse mapToResponse(Plan plan, BigDecimal fundBalance) {

        BigDecimal planAmount = plan.getPlanAmount();

        PlanResponse response = new PlanResponse();

        response.setPlanId(plan.getPlanId());
        response.setPlanAmount(planAmount);
        response.setDisbursedAmount(planAmount.multiply(BigDecimal.valueOf(0.9)));
        response.setInterestAmount(planAmount.multiply(BigDecimal.valueOf(0.1)));
        response.setTotalPayable(planAmount);
        response.setDailyEmi(planAmount.multiply(BigDecimal.valueOf(0.01)));
        response.setDuration(plan.getDurationDays());

        if (fundBalance.compareTo(BigDecimal.ZERO) > 0 &&
                planAmount.compareTo(fundBalance) <= 0) {
            response.setStatus("ACTIVE");
        } else {
            response.setStatus("INACTIVE");
        }

        return response;
    }
}