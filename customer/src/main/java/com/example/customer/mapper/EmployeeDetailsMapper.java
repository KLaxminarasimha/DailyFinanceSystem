
package com.example.customer.mapper;

import com.example.customer.dto.EmployeeDetailsRequest;
import com.example.customer.dto.EmployeeDetailsResponse;
import com.example.customer.entity.EmployeeDetails;

public class EmployeeDetailsMapper {

    // DTO → Entity
    public static EmployeeDetails toEntity(EmployeeDetailsRequest request) {

        EmployeeDetails e = new EmployeeDetails();

        e.setCompanyName(request.getCompanyName());
        e.setEmployeeId(request.getEmployeeId());
        e.setMonthlyIncome(request.getMonthlyIncome());
        e.setExperience(request.getExperience());

        return e;
    }

    // Entity → Response
    public static EmployeeDetailsResponse toResponse(EmployeeDetails e) {

        EmployeeDetailsResponse res = new EmployeeDetailsResponse();

        res.setId(e.getId());
        res.setCompanyName(e.getCompanyName());
        res.setEmployeeId(e.getEmployeeId());
        res.setMonthlyIncome(e.getMonthlyIncome());
        res.setExperience(e.getExperience());

        res.setCustomerId(
                e.getCustomer() != null ? e.getCustomer().getCustomerId() : null
        );

        return res;
    }
}