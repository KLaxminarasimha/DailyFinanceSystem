package com.example.customer.mapper;

import com.example.customer.dto.CreateCustomerRequest;
import com.example.customer.dto.CustomerResponse;
import com.example.customer.dto.GuarantorResponse;
import com.example.customer.entity.Customer;
import com.example.customer.entity.Guarantor;
import com.example.customer.enums.KycStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CustomerMapper {

    // ✅ DTO → ENTITY
    public static Customer toEntity(CreateCustomerRequest request) {

        Customer customer = new Customer();

        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setAddress(request.getAddress());
        customer.setPincode(request.getPincode());
        customer.setDob(request.getDob());
        customer.setGender(request.getGender());

        // 🔥 FIX: set from request (NOT null)
        customer.setUserType(request.getUserType());

        // 🔥 Default values
        customer.setKycStatus(KycStatus.PENDING);

        // 🔥 Timestamps
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());

        return customer;
    }

    // ✅ ENTITY → RESPONSE
    public static CustomerResponse toResponse(Customer customer) {

        CustomerResponse response = new CustomerResponse();

        response.setCustomerId(customer.getCustomerId());
        response.setFirstName(customer.getFirstName());
        response.setLastName(customer.getLastName());
        response.setEmail(customer.getEmail());
        response.setAddress(customer.getAddress());
        response.setPincode(customer.getPincode());
        response.setDob(customer.getDob());
        response.setGender(customer.getGender());

        response.setUserType(customer.getUserType()); // ✅ important
        response.setKycStatus(customer.getKycStatus());

        response.setCreatedAt(customer.getCreatedAt());
        response.setUpdatedAt(customer.getUpdatedAt());

        // ✅ Map guarantors (optional)
        if (customer.getGuarantors() != null && !customer.getGuarantors().isEmpty()) {

            List<GuarantorResponse> guarantorResponses = new ArrayList<>();

            for (Guarantor g : customer.getGuarantors()) {
                guarantorResponses.add(GuarantorMapper.toResponse(g));
            }

            response.setGuarantors(guarantorResponses);
        }

        return response;
    }
}