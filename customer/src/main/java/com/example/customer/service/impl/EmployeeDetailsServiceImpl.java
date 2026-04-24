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
    public EmployeeDetails addEmployee(Long customerId, EmployeeDTO dto) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        if (customer.getUserType() != UserType.EMPLOYEE) {
            throw new RuntimeException("Customer is not an employee");
        }

        EmployeeDetails emp = new EmployeeDetails();
        emp.setEmpId(dto.getEmpId());
        emp.setCompanyName(dto.getCompanyName());
        emp.setCtc(dto.getCtc());
        emp.setMonthlySalary(dto.getMonthlySalary());
        emp.setExperience(dto.getExperience());
        emp.setCustomer(customer);

        return employeeRepository.save(emp);
    }
}