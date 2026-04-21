package com.example.customer.common.validation;

import com.example.customer.common.constants.AppConstants;
import com.example.customer.entity.Customer;
import com.example.customer.enums.UserType;
import com.example.customer.exception.BadRequestException;
import com.example.customer.exception.DuplicateResourceException;
import com.example.customer.exception.ResourceNotFoundException;
import com.example.customer.repository.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidationUtil {

    private final CustomerRepository customerRepository;
    private final GuarantorRepository guarantorRepository;
    private final KycRepository kycRepository;
    private final EmployeeDetailsRepository employeeRepository;
    private final BusinessDetailsRepository businessRepository;

    // =========================
    // CUSTOMER
    // =========================

    public void validateCustomerUniqueness(String email) {
        if (email != null && customerRepository.existsByEmail(email)) {
            throw new DuplicateResourceException(AppConstants.EMAIL_EXISTS);
        }
    }

    // ✅ GET CUSTOMER (USED IN SERVICE)
    public Customer getCustomerOrThrow(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(AppConstants.CUSTOMER_NOT_FOUND)
                );
    }

    // ✅ ADD THIS METHOD (FIXES YOUR ERROR 🔥)
    public void validateCustomerExists(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new ResourceNotFoundException(AppConstants.CUSTOMER_NOT_FOUND);
        }
    }

    // =========================
    // KYC
    // =========================

    public void validateKyc(String aadhar, String pan) {

        if (kycRepository.existsByAadhar(aadhar)) {
            throw new DuplicateResourceException(AppConstants.AADHAR_EXISTS);
        }

        if (kycRepository.existsByPan(pan)) {
            throw new DuplicateResourceException(AppConstants.PAN_EXISTS);
        }
    }

    public void validateKycNotExists(Long customerId) {
        if (kycRepository.findByCustomerCustomerId(customerId).isPresent()) {
            throw new BadRequestException(AppConstants.KYC_ALREADY_EXISTS);
        }
    }

    public void validateKycStatus(String status) {
        if (status == null ||
                (!status.equalsIgnoreCase("VERIFIED") &&
                        !status.equalsIgnoreCase("REJECTED"))) {

            throw new BadRequestException(AppConstants.INVALID_KYC_STATUS);
        }
    }

    // =========================
    // EMPLOYEE
    // =========================

    public void validateEmployee(Customer customer, Long customerId) {

        if (customer.getUserType() != UserType.EMPLOYEE) {
            throw new BadRequestException(AppConstants.INVALID_EMPLOYEE_TYPE);
        }

        if (employeeRepository.existsByCustomerCustomerId(customerId)) {
            throw new BadRequestException(AppConstants.EMPLOYEE_ALREADY_EXISTS);
        }
    }

    // =========================
    // BUSINESS
    // =========================

    public void validateBusiness(Customer customer, Long customerId, String gstNumber) {

        if (customer.getUserType() != UserType.BUSINESS) {
            throw new BadRequestException(AppConstants.INVALID_BUSINESS_TYPE);
        }

        if (businessRepository.existsByCustomerCustomerId(customerId)) {
            throw new BadRequestException(AppConstants.BUSINESS_ALREADY_EXISTS);
        }

        if (businessRepository.existsByGstNumber(gstNumber)) {
            throw new BadRequestException(AppConstants.GST_EXISTS);
        }
    }

    // =========================
    // GUARANTOR
    // =========================

    public void validateGuarantor(String phone, String email, Long customerId, int currentCount) {

        if (guarantorRepository.existsByPhoneAndCustomerCustomerId(phone, customerId)) {
            throw new DuplicateResourceException(AppConstants.GUARANTOR_EXISTS);
        }

        if (guarantorRepository.existsByEmailAndCustomerCustomerId(email, customerId)) {
            throw new DuplicateResourceException(AppConstants.GUARANTOR_EMAIL_EXISTS);
        }

        if (currentCount >= 3) {
            throw new BadRequestException(AppConstants.MAX_GUARANTORS);
        }
    }
}