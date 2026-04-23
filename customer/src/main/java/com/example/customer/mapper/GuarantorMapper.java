package com.example.customer.mapper;

import com.example.customer.dto.CreateGuarantorRequest;
import com.example.customer.dto.GuarantorResponse;
import com.example.customer.entity.Guarantor;

import java.time.LocalDateTime;

public class GuarantorMapper {

    // ✅ DTO → ENTITY
    public static Guarantor toEntity(CreateGuarantorRequest request) {

        Guarantor g = new Guarantor();

        g.setName(request.getName());
        g.setPhone(request.getPhone());
        g.setRelationship(request.getRelationship());
        g.setEmail(request.getEmail());

        // 🔥 NEW: PAN (IMPORTANT)
        g.setPanNumber(
                request.getPanNumber().toUpperCase().trim()
        );

        g.setVerified(false);

        // 🔥 Set timestamps here (optional but clean)
        g.setCreatedAt(LocalDateTime.now());
        g.setUpdatedAt(LocalDateTime.now());

        return g;
    }

    // ✅ ENTITY → RESPONSE
    public static GuarantorResponse toResponse(Guarantor g) {

        GuarantorResponse response = new GuarantorResponse();

        response.setGuarantorId(g.getGuarantorId());
        response.setName(g.getName());
        response.setPhone(g.getPhone());
        response.setRelationship(g.getRelationship());
        response.setEmail(g.getEmail());

        // 🔥 NEW: include PAN in response
        response.setPanNumber(g.getPanNumber());

        response.setVerified(g.getVerified());

        response.setCustomerId(
                g.getCustomer() != null ? g.getCustomer().getCustomerId() : null
        );

        response.setCreatedAt(g.getCreatedAt());
        response.setUpdatedAt(g.getUpdatedAt());

        return response;
    }
}