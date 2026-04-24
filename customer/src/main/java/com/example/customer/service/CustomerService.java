package com.example.customer.service;

import com.example.customer.dto.CustomerDTO;
import com.example.customer.entity.Customer;

import java.util.List;


public interface CustomerService {

    Customer createCustomer(CustomerDTO dto, Long authUserId);

    Customer updateCustomer(Long id, CustomerDTO dto);

    void deleteCustomer(Long id);

    Customer getCustomerById(Long id);

    List<Customer> getAllCustomers();
}