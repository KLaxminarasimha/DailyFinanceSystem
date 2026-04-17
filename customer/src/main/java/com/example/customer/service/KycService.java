
package com.example.customer.service;

import com.example.customer.dto.KycResponse;
import com.example.customer.dto.KycStatusUpdateRequest;
import com.example.customer.dto.KycVerificationRequest;

public interface KycService {


        KycResponse submitKyc(Long customerId, KycVerificationRequest request);

        KycResponse updateKycStatus(Long customerId, KycStatusUpdateRequest request);

}
