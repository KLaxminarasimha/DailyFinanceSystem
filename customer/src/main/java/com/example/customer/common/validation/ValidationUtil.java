package com.example.customer.common.validation;

import com.example.customer.common.constants.AppConstants;
import com.example.customer.entity.Customer;
import com.example.customer.entity.KycDetails;
import com.example.customer.enums.KycStatus;
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

    public void validateCustomerUniqueness(String email, String panNumber) {

        if (email != null) {
            email = email.toLowerCase().trim();
        }

        if (panNumber != null) {
            panNumber = panNumber.toUpperCase().trim();
        }

        // ❌ Email already exists
        if (email != null && customerRepository.existsByEmail(email)) {
            throw new DuplicateResourceException(AppConstants.EMAIL_EXISTS);
        }

        // ❌ PAN already exists in customer
        if (panNumber != null && customerRepository.existsByPanNumber(panNumber)) {
            throw new DuplicateResourceException("Customer with this PAN already exists");
        }

        // ❌ Guarantor cannot become customer
        if (panNumber != null && guarantorRepository.existsByPanNumber(panNumber)) {
            throw new BadRequestException("Guarantor cannot act as customer");
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
    public void validateKyc(String aadhar, String pan, String phone, Customer customer) {

        if (aadhar == null || aadhar.trim().isEmpty()) {
            throw new BadRequestException("Aadhar is required");
        }

        if (pan == null || pan.trim().isEmpty()) {
            throw new BadRequestException("PAN is required");
        }

        if (phone == null || phone.trim().isEmpty()) {
            throw new BadRequestException("Phone is required");
        }

        String normalizedPan = pan.toUpperCase().trim();
        String normalizedAadhar = aadhar.trim();
        String normalizedPhone = phone.trim();

        // 🔹 Aadhar uniqueness
        if (kycRepository.existsByAadhar(normalizedAadhar)) {
            throw new DuplicateResourceException(AppConstants.AADHAR_EXISTS);
        }

        // 🔹 PAN uniqueness (case-insensitive)
        if (kycRepository.existsByPanNumberIgnoreCase(normalizedPan)) {
            throw new DuplicateResourceException("PAN already used in another KYC");
        }

        // 🔹 Phone uniqueness
        if (kycRepository.existsByPhone(normalizedPhone)) {
            throw new DuplicateResourceException("Phone already used in another KYC");
        }



        // 🔹 PAN match
        if (!customer.getPanNumber().equalsIgnoreCase(normalizedPan)) {
            throw new BadRequestException("PAN must match customer PAN");
        }
    }

    // 🔹 CHECK KYC ALREADY EXISTS
    public void validateKycNotExists(Long customerId) {
        if (kycRepository.findByCustomerCustomerId(customerId).isPresent()) {
            throw new BadRequestException(AppConstants.KYC_ALREADY_EXISTS);
        }
    }

    // 🔹 STATUS INPUT VALIDATION
    public void validateKycStatus(String status) {

        if (status == null || status.trim().isEmpty()) {
            throw new BadRequestException("KYC status is required");
        }

        if (!status.equalsIgnoreCase("VERIFIED") &&
                !status.equalsIgnoreCase("REJECTED")) {

            throw new BadRequestException(AppConstants.INVALID_KYC_STATUS);
        }
    }

    // 🔹 STATUS TRANSITION VALIDATION (FIXED)
    public void validateKycAction(KycDetails kyc) {

        if (kyc.getStatus() == KycStatus.VERIFIED) {
            throw new BadRequestException("KYC already verified");
        }

        if (kyc.getStatus() == KycStatus.REJECTED) {
            throw new BadRequestException("KYC already rejected");
        }

        if (kyc.getStatus() != KycStatus.PENDING) {
            throw new BadRequestException("Invalid KYC state");
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

    public void validateGuarantor(String panNumber, Long customerId, int currentCount) {

//        if (guarantorRepository.existsByPhoneAndCustomerCustomerId(phone, customerId)) {
//            throw new DuplicateResourceException(AppConstants.GUARANTOR_EXISTS);
//        }
//
//        if (guarantorRepository.existsByEmailAndCustomerCustomerId(email, customerId)) {
//            throw new DuplicateResourceException(AppConstants.GUARANTOR_EMAIL_EXISTS);
//        }
//
//        if (currentCount >= 3) {
//            throw new BadRequestException(AppConstants.MAX_GUARANTORS);
//        }




            Customer customer = getCustomerOrThrow(customerId);

            // ❌ Same person
            if (customer.getPanNumber().equalsIgnoreCase(panNumber)) {
                throw new RuntimeException("Customer cannot act as guarantor");
            }

            // ❌ PAN already exists globally
            if (customerRepository.existsByPanNumber(panNumber) ||
                    guarantorRepository.existsByPanNumber(panNumber)) {
                throw new RuntimeException("PAN already exists in system");
            }

            // ❌ Limit check (example)
            if (currentCount >= 2) {
                throw new RuntimeException("Guarantor limit exceeded");
            }
        }







}