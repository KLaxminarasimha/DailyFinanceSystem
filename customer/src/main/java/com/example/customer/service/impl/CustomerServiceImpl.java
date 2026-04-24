package com.example.customer.service.impl;

import com.example.customer.dto.CustomerDTO;
import com.example.customer.entity.Customer;
import com.example.customer.enums.UserType;
import com.example.customer.repository.CustomerRepository;
import com.example.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;

    // ✅ CREATE
    @Override
    public Customer createCustomer(CustomerDTO dto, Long authUserId) {

        if (repository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Customer already exists");
        }

        Customer customer = new Customer();

        mapDtoToEntity(dto, customer);
        customer.setAuthUserId(authUserId);

        return repository.save(customer);
    }

    // ✅ UPDATE
    @Override
    public Customer updateCustomer(Long id, CustomerDTO dto) {

        Customer customer = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        mapDtoToEntity(dto, customer);

        return repository.save(customer);
    }

    // ✅ DELETE
    @Override
    public void deleteCustomer(Long id) {

        Customer customer = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        repository.delete(customer);
    }

    // ✅ GET BY ID
    @Override
    public Customer getCustomerById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    // ✅ GET ALL
    @Override
    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }

    // 🔹 COMMON MAPPING METHOD (VERY IMPORTANT)
    private void mapDtoToEntity(CustomerDTO dto, Customer customer) {

        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        customer.setDob(dto.getDob());
        customer.setGender(dto.getGender());
        customer.setAddress(dto.getAddress());
        customer.setCity(dto.getCity());
        customer.setState(dto.getState());
        customer.setPincode(dto.getPincode());
        customer.setUserType(UserType.valueOf(dto.getUserType().toUpperCase()));
    }
}