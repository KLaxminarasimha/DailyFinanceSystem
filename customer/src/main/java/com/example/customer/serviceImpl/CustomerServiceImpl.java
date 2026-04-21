package com.example.customer.serviceImpl;

import com.example.customer.common.validation.ValidationUtil;
import com.example.customer.dto.CreateCustomerRequest;
import com.example.customer.dto.CustomerResponse;
import com.example.customer.dto.UpdateCustomerRequest;
import com.example.customer.entity.Customer;
import com.example.customer.mapper.CustomerMapper;
import com.example.customer.repository.CustomerRepository;
import com.example.customer.service.CustomerService;

import com.example.customer.service.EmailService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {
    private final EmailService emailService;

    private final CustomerRepository customerRepository;
    private final ValidationUtil validationUtil;

    // ✅ CREATE CUSTOMER
    @Override
    public CustomerResponse createCustomer(CreateCustomerRequest request) {

        // 🔥 Normalize inputs
        String email = request.getEmail() != null
                ? request.getEmail().toLowerCase().trim()
                : null;

        String pan = request.getPanNumber() != null
                ? request.getPanNumber().toUpperCase().trim()
                : null;

        // 🔥 Validate uniqueness
        validationUtil.validateCustomerUniqueness(email, pan);

        // 🔥 Map DTO → Entity
        Customer customer = CustomerMapper.toEntity(request);

        // 🔥 Ensure normalized values are saved
        customer.setEmail(email);
        customer.setPanNumber(pan);

        // 🔥 Save

        Customer savedCustomer = customerRepository.save(customer);

// ✅ SEND EMAIL (ADD THIS BLOCK)
        String subject = "Customer Registration Successful";

        String body = "Hello " + savedCustomer.getFirstName() + ",\n\n"
                + "Your account has been created successfully.\n\n"
                + "Customer ID: " + savedCustomer.getCustomerId() + "\n"
                + "Thank you!";

        emailService.sendEmail(
                savedCustomer.getEmail(),
                subject,
                body
        );

// 🔥 Return response
        return CustomerMapper.toResponse(savedCustomer);


    }

    // ✅ GET ALL CUSTOMERS (Pagination + Sorting)
    @Override
    @Transactional(readOnly = true)
    public Page<CustomerResponse> getAllCustomers(int page, int size) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("createdAt").descending()
        );

        Page<Customer> customers = customerRepository.findAll(pageable);

        return customers.map(CustomerMapper::toResponse);
    }

    // ✅ GET CUSTOMER BY ID
    @Override
    @Transactional(readOnly = true)
    public CustomerResponse getCustomerById(Long id) {

        Customer customer = validationUtil.getCustomerOrThrow(id);

        return CustomerMapper.toResponse(customer);
    }

    // ✅ UPDATE CUSTOMER
    @Override
    public CustomerResponse updateCustomer(Long id, UpdateCustomerRequest request) {

        // 🔥 STEP 1: Get existing customer
        Customer customer = validationUtil.getCustomerOrThrow(id);

        // 🔥 Normalize inputs
        String newEmail = request.getEmail() != null
                ? request.getEmail().toLowerCase().trim()
                : null;

        String newPan = request.getPanNumber() != null
                ? request.getPanNumber().toUpperCase().trim()
                : null;

        // 🔥 STEP 2: Email validation
        if (newEmail != null &&
                !newEmail.equals(customer.getEmail())) {

            validationUtil.validateCustomerUniqueness(newEmail, null);
            customer.setEmail(newEmail);
        }

        // 🔥 STEP 3: PAN validation
        if (newPan != null &&
                !newPan.equals(customer.getPanNumber())) {

            validationUtil.validateCustomerUniqueness(null, newPan);
            customer.setPanNumber(newPan);
        }

        // 🔥 STEP 4: Update other fields
        if (request.getFirstName() != null) {
            customer.setFirstName(request.getFirstName());
        }

        if (request.getLastName() != null) {
            customer.setLastName(request.getLastName());
        }

        if (request.getAddress() != null) {
            customer.setAddress(request.getAddress());
        }

        if (request.getPincode() != null) {
            customer.setPincode(request.getPincode());
        }

        if (request.getDob() != null) {
            customer.setDob(request.getDob());
        }

        if (request.getGender() != null) {
            customer.setGender(request.getGender());
        }

        if (request.getUserType() != null) {
            customer.setUserType(request.getUserType());
        }

        // 🔥 STEP 5: Update timestamp
        customer.setUpdatedAt(LocalDateTime.now());

        // 🔥 STEP 6: Save
        Customer updatedCustomer = customerRepository.save(customer);

        return CustomerMapper.toResponse(updatedCustomer);
    }

    // ✅ DELETE CUSTOMER
    @Override
    public void deleteCustomer(Long id) {

        Customer customer = validationUtil.getCustomerOrThrow(id);

        customerRepository.delete(customer);
    }
}