package com.example.customer.service.impl;

import com.example.customer.dto.BusinessDTO;
import com.example.customer.entity.BusinessDetails;
import com.example.customer.entity.Customer;
import com.example.customer.enums.UserType;
import com.example.customer.repository.BusinessRepository;
import com.example.customer.repository.CustomerRepository;
import com.example.customer.service.BusinessDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BusinessDetailsServiceImpl implements BusinessDetailsService {

    private final CustomerRepository customerRepository;
    private final BusinessRepository businessRepository;

    @Override
    public BusinessDetails addBusinessByUserId(Long userId, BusinessDTO dto) {

        Customer customer = customerRepository.findByAuthUserId(userId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        if (customer.getUserType() != UserType.BUSINESS) {
            throw new RuntimeException("Not a business user");
        }

        BusinessDetails business = new BusinessDetails();
        business.setBusinessName(dto.getBusinessName());
        business.setBusinessType(dto.getBusinessType());
        business.setGstNumber(dto.getGstNumber());
        business.setMonthlyIncome(dto.getMonthlyIncome());
        business.setCustomer(customer);

        return businessRepository.save(business);
    }
}