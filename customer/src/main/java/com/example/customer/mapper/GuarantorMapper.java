package com.example.customer.mapper;

import com.example.customer.dto.CreateGuarantorRequest;
import com.example.customer.dto.GuarantorResponse;
import com.example.customer.entity.Guarantor;

public class GuarantorMapper {

    public static Guarantor toEntity(CreateGuarantorRequest request) {

        Guarantor g = new Guarantor();

        g.setName(request.getName());
        g.setPhone(request.getPhone());
        g.setRelationship(request.getRelationship());
        g.setEmail(request.getEmail());
        g.setVerified(false);

        return g;
    }

    public static GuarantorResponse toResponse(Guarantor g) {

        GuarantorResponse response = new GuarantorResponse();

        response.setGuarantorId(g.getGuarantorId());
        response.setName(g.getName());
        response.setPhone(g.getPhone());
        response.setRelationship(g.getRelationship());
        response.setEmail(g.getEmail());
        response.setVerified(g.getVerified());

        response.setCustomerId(
                g.getCustomer() != null ? g.getCustomer().getCustomerId() : null
        );

        response.setCreatedAt(g.getCreatedAt());
        response.setUpdatedAt(g.getUpdatedAt());

        return response;
    }
}