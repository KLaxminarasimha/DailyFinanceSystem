package com.example.customer.mapper;

import com.example.customer.dto.KycVerificationRequest;
import com.example.customer.dto.KycResponse;
import com.example.customer.entity.KycDetails;
import com.example.customer.enums.KycStatus;

public class KycMapper {

    public static KycDetails toEntity(KycVerificationRequest request) {

        KycDetails kyc = new KycDetails();

        // ✅ Clean + normalize data
        kyc.setAadhar(request.getAadhar().trim());
        kyc.setPanNumber(request.getPanNumber().toUpperCase().trim());
        kyc.setPhone(request.getPhone().trim());

        // ✅ Default status
        kyc.setStatus(KycStatus.PENDING);

        return kyc;
    }

    public static KycResponse toResponse(KycDetails kyc) {

        KycResponse res = new KycResponse();

        res.setId(kyc.getId());
        res.setAadhar(kyc.getAadhar());
        res.setPanNumber(kyc.getPanNumber());
        res.setPhone(kyc.getPhone());
        res.setStatus(kyc.getStatus());
        res.setRejectionReason(kyc.getRejectionReason());

        res.setCustomerId(
                kyc.getCustomer() != null
                        ? kyc.getCustomer().getCustomerId()
                        : null
        );

        return res;
    }
}