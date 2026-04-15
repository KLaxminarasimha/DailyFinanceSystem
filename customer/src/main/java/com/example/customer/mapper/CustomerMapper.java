
package com.example.customer.mapper;

import com.example.customer.dto.*;
import com.example.customer.entity.Customer;
import com.example.customer.entity.Guarantor;

import java.util.ArrayList;
import java.util.List;

public class CustomerMapper {

    //  DTO → ENTITY
    public static Customer toEntity(CreateCustomerRequest request) {

        Customer customer = new Customer();

        customer.setName(request.getName());
        customer.setPhone(request.getPhone());
        customer.setEmail(request.getEmail());
        customer.setAadhar(request.getAadhar());
        customer.setPan(request.getPan());
        customer.setIncome(request.getIncome());
        customer.setAddress(request.getAddress());

        if (request.getGuarantors() != null) {
            List<Guarantor> guarantors = new ArrayList<>();

            for (CreateGuarantorRequest g : request.getGuarantors()) {
                guarantors.add(GuarantorMapper.toEntity(g));
            }

            customer.setGuarantors(guarantors);
        }

        return customer;
    }

    //  ENTITY → RESPONSE
    public static CustomerResponse toResponse(Customer customer) {

        CustomerResponse response = new CustomerResponse();

        response.setCustomerId(customer.getCustomerId());
        response.setName(customer.getName());
        response.setPhone(customer.getPhone());
        response.setEmail(customer.getEmail());
        response.setAddress(customer.getAddress());
        response.setIncome(customer.getIncome());
        response.setKycStatus(customer.getKycStatus());
        response.setCreatedAt(customer.getCreatedAt());
        response.setUpdatedAt(customer.getUpdatedAt());

        if (customer.getGuarantors() != null) {
            List<GuarantorResponse> guarantorResponses = new ArrayList<>();

            for (Guarantor g : customer.getGuarantors()) {
                guarantorResponses.add(GuarantorMapper.toResponse(g));
            }

//            response.setGuarantors(guarantorResponses);
//            response.setGuarantors(null);
        }

        return response;
    }
}