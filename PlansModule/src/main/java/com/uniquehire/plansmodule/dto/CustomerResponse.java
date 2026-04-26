package com.uniquehire.plansmodule.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CustomerResponse {

    private Long id;
    private String userType;

    private EmployeeDetails employeeDetails;
    private BusinessDetails businessDetails;

    @Data
    public static class EmployeeDetails {
        private String empId;
        private String companyName;
        private BigDecimal ctc;
        private BigDecimal monthlySalary;
        private Integer experience;
    }

    @Data
    public static class BusinessDetails {
        private BigDecimal monthlyIncome;
        private String businessName;
        private String businessType;
        private String gstNumber;
    }
}