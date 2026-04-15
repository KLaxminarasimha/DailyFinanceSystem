package com.example.customer.serviceImpl;

import com.example.customer.dto.*;
import com.example.customer.entity.Customer;
import com.example.customer.entity.Guarantor;
import com.example.customer.exception.DuplicateResourceException;
import com.example.customer.exception.ResourceNotFoundException;
import com.example.customer.mapper.CustomerMapper;
import com.example.customer.repository.CustomerRepository;
import com.example.customer.service.CustomerService;
import com.example.customer.common.constants.AppConstants;
import com.example.customer.common.validation.ValidationUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ValidationUtil validationUtil;

    //  CREATE CUSTOMER
    @Override
    public CustomerResponse createCustomer(CreateCustomerRequest request) {

        // 🔥 USING VALIDATION UTIL
        validationUtil.validateCustomerUniqueness(
                request.getPhone(),
                request.getEmail(),
                request.getAadhar(),
                request.getPan()
        );

        Customer customer = CustomerMapper.toEntity(request);

        if (customer.getGuarantors() != null) {
            for (Guarantor g : customer.getGuarantors()) {
                g.setCustomer(customer);
            }
        }

        Customer savedCustomer = customerRepository.save(customer);

        return CustomerMapper.toResponse(savedCustomer);
    }

//    //  GET ALL CUSTOMERS
//    @Override
//    @Transactional(readOnly = true)
//    public Page<CustomerResponse> getAllCustomers(int page, int size) {
//
//        Pageable pageable = PageRequest.of(page, size,
//                Sort.by(Sort.Direction.DESC, "customerId"));
//
//        return customerRepository.findAll(pageable)
//                .map(CustomerMapper::toResponse);
////    }

// ✅ GET ALL CUSTOMERS (FIXED)
@Override
@Transactional
public Page<CustomerResponse> getAllCustomers(int page, int size) {

    Pageable pageable = PageRequest.of(page, size);

    Page<Customer> customers = customerRepository.findAll(pageable);

    return customers.map(CustomerMapper::toResponse);
}

    //  GET CUSTOMER BY ID
    @Override
    @Transactional(readOnly = true)
    public CustomerResponse getCustomerById(Long id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(AppConstants.CUSTOMER_NOT_FOUND));

        return CustomerMapper.toResponse(customer);
    }

    //  UPDATE CUSTOMER
    @Override
    public CustomerResponse updateCustomer(Long id, UpdateCustomerRequest request) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(AppConstants.CUSTOMER_NOT_FOUND));

        // EMAIL DUPLICATE CHECK
        if (request.getEmail() != null &&
                !request.getEmail().equals(customer.getEmail()) &&
                customerRepository.existsByEmail(request.getEmail())) {

            throw new DuplicateResourceException(AppConstants.EMAIL_EXISTS);
        }

        if (request.getName() != null) {
            customer.setName(request.getName());
        }

        if (request.getEmail() != null) {
            customer.setEmail(request.getEmail());
        }

        if (request.getIncome() != null) {
            customer.setIncome(request.getIncome());
        }

        if (request.getAddress() != null) {
            customer.setAddress(request.getAddress());
        }

        Customer updatedCustomer = customerRepository.save(customer);

        return CustomerMapper.toResponse(updatedCustomer);
    }

    //  DELETE CUSTOMER
    @Override
    public void deleteCustomer(Long id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(AppConstants.CUSTOMER_NOT_FOUND));

        customerRepository.delete(customer);
    }
}