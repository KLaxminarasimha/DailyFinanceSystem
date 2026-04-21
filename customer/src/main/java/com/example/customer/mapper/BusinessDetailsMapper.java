package com.example.customer.mapper;

import com.example.customer.dto.BusinessDetailsRequest;
import com.example.customer.dto.BusinessDetailsResponse;
import com.example.customer.entity.BusinessDetails;

public class BusinessDetailsMapper {

    // DTO → Entity
    public static BusinessDetails toEntity(BusinessDetailsRequest request) {

        BusinessDetails b = new BusinessDetails();

        b.setBusinessName(request.getBusinessName());
        b.setBusinessType(request.getBusinessType());
        b.setGstNumber(request.getGstNumber());
        b.setMonthlyIncome(request.getMonthlyIncome());
        b.setYearsInBusiness(request.getYearsInBusiness());

        return b;
    }

    // Entity → Response
    public static BusinessDetailsResponse toResponse(BusinessDetails b) {

        BusinessDetailsResponse res = new BusinessDetailsResponse();

        res.setId(b.getId());
        res.setBusinessName(b.getBusinessName());
        res.setBusinessType(b.getBusinessType());
        res.setGstNumber(b.getGstNumber());
        res.setMonthlyIncome(b.getMonthlyIncome());
        res.setYearsInBusiness(b.getYearsInBusiness());

        res.setCustomerId(
                b.getCustomer() != null ? b.getCustomer().getCustomerId() : null
        );

        return res;
    }
}