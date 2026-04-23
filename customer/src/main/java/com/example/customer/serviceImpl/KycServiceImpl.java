package com.example.customer.serviceImpl;

import com.example.customer.common.constants.AppConstants; // ✅ ADD
import com.example.customer.common.validation.ValidationUtil;
import com.example.customer.dto.KycResponse;
import com.example.customer.dto.KycStatusUpdateRequest;
import com.example.customer.dto.KycVerificationRequest;
import com.example.customer.entity.Customer;
import com.example.customer.entity.KycDetails;
import com.example.customer.enums.KycStatus;
import com.example.customer.exception.BadRequestException;
import com.example.customer.exception.ResourceNotFoundException;
import com.example.customer.mapper.KycMapper;
import com.example.customer.repository.CustomerRepository; // ✅ ADD
import com.example.customer.repository.KycRepository;
import com.example.customer.service.KycService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class KycServiceImpl implements KycService {

    private final KycRepository kycRepository;
    private final CustomerRepository customerRepository; // ✅ ADD
    private final ValidationUtil validationUtil;

    // ✅ SUBMIT KYC
    @Override
    public KycResponse submitKyc(Long customerId, KycVerificationRequest request) {

        Customer customer = validationUtil.getCustomerOrThrow(customerId);

        validationUtil.validateKycNotExists(customerId);
        validationUtil.validateKyc(
                request.getAadhar(),
                request.getPanNumber(),
                request.getPhone(),   // ✅ ADD THIS
                customer
        );

        KycDetails kyc = KycMapper.toEntity(request);
        kyc.setCustomer(customer);

        kycRepository.save(kyc);

        // 🔥 FIX: SAVE CUSTOMER
        customer.setKycStatus(KycStatus.PENDING);
        customerRepository.save(customer); // ✅ IMPORTANT

        return KycMapper.toResponse(kyc);
    }

    @Override
    public KycResponse updateKycStatus(Long customerId, KycStatusUpdateRequest request) {

        validationUtil.validateKycStatus(request.getStatus());

        // ✅ ADD HERE (FIRST DB CALL)
        Customer customer = validationUtil.getCustomerOrThrow(customerId);

        KycDetails kyc = kycRepository.findByCustomerCustomerId(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(AppConstants.KYC_NOT_FOUND)
                );

        validationUtil.validateKycAction(kyc);

        // ❌ REMOVE THIS LINE
        // Customer customer = kyc.getCustomer();

        // ✅ Convert to ENUM (clean approach)
        KycStatus status = KycStatus.valueOf(request.getStatus().toUpperCase());

        if (status == KycStatus.VERIFIED) {

            kyc.setStatus(KycStatus.VERIFIED);
            customer.setKycStatus(KycStatus.VERIFIED);

        } else {

            // ✅ rejection reason validation
            if (request.getRejectionReason() == null || request.getRejectionReason().trim().isEmpty()) {
                throw new BadRequestException("Rejection reason is required when KYC is rejected");
            }

            kyc.setStatus(KycStatus.REJECTED);
            kyc.setRejectionReason(request.getRejectionReason());
            customer.setKycStatus(KycStatus.REJECTED);
        }

        kycRepository.save(kyc);
        customerRepository.save(customer);

        return KycMapper.toResponse(kyc);
    }
}