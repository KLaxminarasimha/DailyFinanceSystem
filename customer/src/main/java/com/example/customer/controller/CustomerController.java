package com.example.customer.controller;

import com.example.customer.dto.CustomerDTO;
import com.example.customer.dto.CustomerResponse;
import com.example.customer.entity.Customer;
import com.example.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    // ✅ CREATE
    @PostMapping("/profile")
    public Customer createCustomer(@RequestBody CustomerDTO dto,
                                   @RequestHeader("X-USER-ID") Long authUserId) {
        return service.createCustomer(dto, authUserId);
    }

    // ✅ UPDATE
    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable Long id,
                                   @RequestBody CustomerDTO dto) {
        return service.updateCustomer(id, dto);
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        service.deleteCustomer(id);
        return "Customer deleted successfully";
    }

    // ✅ GET BY ID (FIXED)
    @GetMapping("/{id}")
    public CustomerResponse getCustomerById(@PathVariable Long id) {
        return service.getCustomerById(id);
    }

    // ✅ GET ALL
    @GetMapping
    public List<Customer> getAllCustomers() {
        return service.getAllCustomers();
    }
}