package com.example.customer.service.impl;

import com.example.customer.dto.EmployeeDTO;
import com.example.customer.entity.Customer;
import com.example.customer.entity.EmployeeDetails;
import com.example.customer.enums.UserType;
import com.example.customer.repository.CustomerRepository;
import com.example.customer.repository.EmployeeRepository;
import com.example.customer.service.EmployeeDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeDetailsServiceImpl implements EmployeeDetailsService {

    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public EmployeeDetails addEmployeeByUserId(Long userId, EmployeeDTO dto) {

        Customer customer = customerRepository.findByAuthUserId(userId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // 🔥 Prevent duplicate employee details
        if (customer.getEmployeeDetails() != null) {
            throw new RuntimeException("Employee details already exist");
        }

        EmployeeDetails emp = new EmployeeDetails();

        emp.setEmpId(dto.getEmpId());
        emp.setCompanyName(dto.getCompanyName());
        emp.setMonthlySalary(dto.getMonthlySalary());
        emp.setExperience(dto.getExperience());

        // 🔥 VERY IMPORTANT (BOTH SIDES)
        emp.setCustomer(customer);
        customer.setEmployeeDetails(emp);

        return employeeRepository.save(emp);
    }
}