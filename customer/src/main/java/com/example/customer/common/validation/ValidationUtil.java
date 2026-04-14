package com.example.customer.common.validation;

import com.example.customer.common.constants.AppConstants;
import com.example.customer.exception.BadRequestException;
import com.example.customer.exception.DuplicateResourceException;
import com.example.customer.exception.ResourceNotFoundException;
import com.example.customer.repository.CustomerRepository;
import com.example.customer.repository.GuarantorRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidationUtil {

    private final CustomerRepository customerRepository;
    private final GuarantorRepository guarantorRepository;

    //  CUSTOMER VALIDATION
    public void validateCustomerUniqueness(String phone, String email, String aadhar, String pan) {

        if (customerRepository.existsByPhone(phone)) {
            throw new DuplicateResourceException(AppConstants.PHONE_EXISTS);
        }

        if (email != null && customerRepository.existsByEmail(email)) {
            throw new DuplicateResourceException(AppConstants.EMAIL_EXISTS);
        }

        if (customerRepository.existsByAadhar(aadhar)) {
            throw new DuplicateResourceException(AppConstants.AADHAR_EXISTS);
        }

        if (customerRepository.existsByPan(pan)) {
            throw new DuplicateResourceException(AppConstants.PAN_EXISTS);
        }
    }

    //  GUARANTOR VALIDATION
    public void validateGuarantor(String phone, Long customerId, int currentCount) {

        //  Duplicate guarantor for same customer
        if (guarantorRepository.existsByPhoneAndCustomerCustomerId(phone, customerId)) {
            throw new DuplicateResourceException(AppConstants.GUARANTOR_EXISTS);
        }

        //  Limit guarantors
        if (currentCount >= 3) {
            throw new BadRequestException(AppConstants.MAX_GUARANTORS);
        }
    }

    //  CUSTOMER EXISTENCE
    public void validateCustomerExists(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new ResourceNotFoundException(AppConstants.CUSTOMER_NOT_FOUND);
        }
    }
}