package com.example.customer.service.impl;

import com.example.customer.dto.BusinessDTO;
import com.example.customer.dto.CustomerDTO;
import com.example.customer.dto.CustomerResponse;
import com.example.customer.dto.EmployeeDTO;
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

        if (repository.existsByAuthUserId(authUserId)) {
            return repository.findByAuthUserId(authUserId)
                    .orElseThrow(() -> new RuntimeException("Customer not found"));
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

    // ✅ GET BY ID (FIXED)
    @Override
    public CustomerResponse getCustomerById(Long id) {

        Customer customer = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        CustomerResponse response = new CustomerResponse();

        response.setId(customer.getId());
        response.setUserType(customer.getUserType().name());

        response.setFirstName(customer.getFirstName());
        response.setLastName(customer.getLastName());

        // EMPLOYEE
        if (customer.getUserType() == UserType.EMPLOYEE) {

            EmployeeDTO emp = new EmployeeDTO();

            if (customer.getEmployeeDetails() != null) {
                emp.setMonthlySalary(customer.getEmployeeDetails().getMonthlySalary());
                emp.setCompanyName(customer.getEmployeeDetails().getCompanyName());
                emp.setEmpId(customer.getEmployeeDetails().getEmpId());
                emp.setExperience(customer.getEmployeeDetails().getExperience());
            }

            response.setEmployeeDetails(emp);
        }

        // BUSINESS
        if (customer.getUserType() == UserType.BUSINESS) {

            BusinessDTO bus = new BusinessDTO();

            if (customer.getBusinessDetails() != null) {
                bus.setMonthlyIncome(customer.getBusinessDetails().getMonthlyIncome());
                bus.setBusinessName(customer.getBusinessDetails().getBusinessName());
                bus.setBusinessType(customer.getBusinessDetails().getBusinessType());
                bus.setGstNumber(customer.getBusinessDetails().getGstNumber());
            }

            response.setBusinessDetails(bus);
        }

        return response;
    }

    // ✅ GET ALL
    @Override
    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }

    // 🔹 COMMON MAPPING METHOD
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

    public CustomerResponse getCustomerByUserId(Long userId) {

        Customer customer = repository.findByAuthUserId(userId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        return getCustomerById(customer.getId()); // reuse existing method
    }
    @Override
    public Customer findByUserId(Long userId) {
        return repository.findByAuthUserId(userId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }
}