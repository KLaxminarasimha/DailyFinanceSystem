package com.example.customer.service;

import com.example.customer.dto.CustomerDTO;
import com.example.customer.dto.CustomerResponse;
import com.example.customer.entity.Customer;

import java.util.List;


public interface CustomerService {

    Customer createCustomer(CustomerDTO dto, Long authUserId);

    Customer updateCustomer(Long id, CustomerDTO dto);

    void deleteCustomer(Long id);

    CustomerResponse getCustomerById(Long id);

    List<Customer> getAllCustomers();

    CustomerResponse getCustomerByUserId(Long userId);
    Customer findByUserId(Long userId);
}