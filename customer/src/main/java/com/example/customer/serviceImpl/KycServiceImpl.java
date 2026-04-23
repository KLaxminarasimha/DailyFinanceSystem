package com.example.customer.serviceImpl;

import com.example.customer.common.constants.AppConstants; // ✅ ADD
import com.example.customer.common.validation.ValidationUtil;
import com.example.customer.dto.KycResponse;
import com.example.customer.dto.KycStatusUpdateRequest;
import com.example.customer.dto.KycVerificationRequest;
import com.example.customer.entity.Customer;
import com.example.customer.entity.KycDetails;
import com.example.customer.enums.KycStatus;
import com.example.customer.exception.ResourceNotFoundException;
import com.example.customer.mapper.KycMapper;
import com.example.customer.repository.CustomerRepository; // ✅ ADD
import com.example.customer.repository.KycRepository;
import com.example.customer.service.KycService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KycServiceImpl implements KycService {

    private final KycRepository kycRepository;
    private final CustomerRepository customerRepository; // ✅ ADD
    private final ValidationUtil validationUtil;

    // ✅ SUBMIT KYC
    @Override
    public KycResponse submitKyc(Long customerId, KycVerificationRequest request) {

        Customer customer = validationUtil.getCustomerOrThrow(customerId);

        validationUtil.validateKycNotExists(customerId);
        validationUtil.validateKyc(request.getAadhar(), request.getPan());

        KycDetails kyc = KycMapper.toEntity(request);
        kyc.setCustomer(customer);

        kycRepository.save(kyc);

        // 🔥 FIX: SAVE CUSTOMER
        customer.setKycStatus(KycStatus.IN_PROGRESS);
        customerRepository.save(customer); // ✅ IMPORTANT

        return KycMapper.toResponse(kyc);
    }

    // ✅ UPDATE KYC STATUS
    @Override
    public KycResponse updateKycStatus(Long customerId, KycStatusUpdateRequest request) {

        validationUtil.validateKycStatus(request.getStatus());

        KycDetails kyc = kycRepository.findByCustomerCustomerId(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(AppConstants.KYC_NOT_FOUND) // ✅ FIX
                );

        Customer customer = kyc.getCustomer();

        if ("VERIFIED".equalsIgnoreCase(request.getStatus())) {

            kyc.setStatus(KycStatus.VERIFIED);
            customer.setKycStatus(KycStatus.VERIFIED);

        } else {

            kyc.setStatus(KycStatus.REJECTED);
            kyc.setRejectionReason(request.getRejectionReason());
            customer.setKycStatus(KycStatus.REJECTED);
        }

        kycRepository.save(kyc);

        // 🔥 FIX: SAVE CUSTOMER
        customerRepository.save(customer); // ✅ IMPORTANT

        return KycMapper.toResponse(kyc);
    }
}