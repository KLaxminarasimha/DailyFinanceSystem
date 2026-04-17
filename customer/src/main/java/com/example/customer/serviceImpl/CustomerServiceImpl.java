package com.example.customer.serviceImpl;

import com.example.customer.common.validation.ValidationUtil;
import com.example.customer.dto.CreateCustomerRequest;
import com.example.customer.dto.CustomerResponse;
import com.example.customer.dto.UpdateCustomerRequest;
import com.example.customer.entity.Customer;
import com.example.customer.mapper.CustomerMapper;
import com.example.customer.repository.CustomerRepository;
import com.example.customer.service.CustomerService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ValidationUtil validationUtil;

    // ✅ CREATE CUSTOMER
    @Override
    public CustomerResponse createCustomer(CreateCustomerRequest request) {

        // 🔥 Validate uniqueness
        validationUtil.validateCustomerUniqueness(request.getEmail());

        // 🔥 Map DTO → Entity
        Customer customer = CustomerMapper.toEntity(request);

        // 🔥 Save
        Customer savedCustomer = customerRepository.save(customer);

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
                Sort.by("createdAt").descending() // 🔥 improvement
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

    // ✅ UPDATE CUSTOMER (FINAL CLEAN VERSION 🔥)
    @Override
    public CustomerResponse updateCustomer(Long id, UpdateCustomerRequest request) {

        // 🔥 STEP 1: Get existing customer
        Customer customer = validationUtil.getCustomerOrThrow(id);

        // 🔥 STEP 2: Email validation (case-insensitive)
        if (request.getEmail() != null &&
                !request.getEmail().equalsIgnoreCase(customer.getEmail())) {

            validationUtil.validateCustomerUniqueness(request.getEmail());
            customer.setEmail(request.getEmail());
        }

        // 🔥 STEP 3: Update fields safely
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

        // 🔥 NEW: update userType
        if (request.getUserType() != null) {
            customer.setUserType(request.getUserType());
        }

        // 🔥 Always update timestamp
        customer.setUpdatedAt(LocalDateTime.now());

        // 🔥 Save updated entity
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