package com.example.customer.mapper;

import com.example.customer.dto.KycVerificationRequest;
import com.example.customer.dto.KycResponse;
import com.example.customer.entity.KycDetails;
import com.example.customer.enums.KycStatus;

public class KycMapper {

    public static KycDetails toEntity(KycVerificationRequest request) {

        KycDetails kyc = new KycDetails();

        kyc.setAadhar(request.getAadhar());
        kyc.setPan(request.getPan());
        kyc.setPhone(request.getPhone());

        // 🔥 Default when submitted
        kyc.setStatus(KycStatus.IN_PROGRESS);

        return kyc;
    }

    public static KycResponse toResponse(KycDetails kyc) {

        KycResponse res = new KycResponse();

        res.setId(kyc.getId());
        res.setAadhar(kyc.getAadhar());
        res.setPan(kyc.getPan());
        res.setPhone(kyc.getPhone());
        res.setStatus(kyc.getStatus());
        res.setRejectionReason(kyc.getRejectionReason());
        res.setCustomerId(
                kyc.getCustomer() != null ? kyc.getCustomer().getCustomerId() : null
        );

        return res;
    }
}