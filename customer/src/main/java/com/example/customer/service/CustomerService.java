package com.example.customer.service;

import com.example.customer.dto.CreateCustomerRequest;
import com.example.customer.dto.CustomerResponse;
import com.example.customer.dto.UpdateCustomerRequest;
import com.example.customer.entity.Customer;
import org.springframework.data.domain.Page;

public interface CustomerService {

    CustomerResponse createCustomer(CreateCustomerRequest request);
    Page<CustomerResponse> getAllCustomers(int page, int size);
    CustomerResponse getCustomerById(Long id);
    CustomerResponse updateCustomer(Long id, UpdateCustomerRequest request);
    void deleteCustomer(Long id);


}
