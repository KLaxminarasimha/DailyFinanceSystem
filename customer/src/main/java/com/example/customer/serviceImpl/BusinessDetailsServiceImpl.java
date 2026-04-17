package com.example.customer.serviceImpl;

import com.example.customer.common.constants.AppConstants;
import com.example.customer.common.validation.ValidationUtil;
import com.example.customer.dto.BusinessDetailsRequest;
import com.example.customer.dto.BusinessDetailsResponse;
import com.example.customer.entity.BusinessDetails;
import com.example.customer.entity.Customer;
import com.example.customer.exception.ResourceNotFoundException;
import com.example.customer.mapper.BusinessDetailsMapper;
import com.example.customer.repository.BusinessDetailsRepository;
import com.example.customer.service.BusinessDetailsService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BusinessDetailsServiceImpl implements BusinessDetailsService {

    private final BusinessDetailsRepository businessRepository;
    private final ValidationUtil validationUtil;

    // ✅ ADD BUSINESS DETAILS
    @Override
    public BusinessDetailsResponse addBusinessDetails(Long customerId, BusinessDetailsRequest request) {

        // 🔥 STEP 1: Get customer
        Customer customer = validationUtil.getCustomerOrThrow(customerId);

        // 🔥 STEP 2: Validate
        validationUtil.validateBusiness(customer, customerId, request.getGstNumber());

        // STEP 3: Save
        BusinessDetails b = BusinessDetailsMapper.toEntity(request);
        b.setCustomer(customer);

        businessRepository.save(b);

        return BusinessDetailsMapper.toResponse(b);
    }

    // ✅ GET BUSINESS DETAILS
    @Override
    public BusinessDetailsResponse getBusinessDetails(Long customerId) {

        BusinessDetails b = businessRepository.findByCustomerCustomerId(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(AppConstants.BUSINESS_NOT_FOUND)
                );

        return BusinessDetailsMapper.toResponse(b);
    }
}