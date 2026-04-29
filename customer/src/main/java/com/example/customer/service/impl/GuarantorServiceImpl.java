package com.example.customer.service.impl;

import com.example.customer.dto.GuarantorDTO;
import com.example.customer.entity.Customer;
import com.example.customer.entity.Guarantor;
import com.example.customer.repository.CustomerRepository;
import com.example.customer.repository.GuarantorRepository;
import com.example.customer.service.GuarantorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuarantorServiceImpl implements GuarantorService {

    private final CustomerRepository customerRepository;
    private final GuarantorRepository guarantorRepository;

    @Override
    public Guarantor addGuarantorByUserId(Long userId, GuarantorDTO dto) {

        Customer customer = customerRepository.findByAuthUserId(userId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        if (customerRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Guarantor cannot be a registered customer");
        }

        Guarantor g = new Guarantor();
        g.setName(dto.getName());
        g.setPhone(dto.getPhone());
        g.setEmail(dto.getEmail());
        g.setPan(dto.getPan());
        g.setRelation(dto.getRelation());
        g.setAddress(dto.getAddress());
        g.setCustomer(customer);

        return guarantorRepository.save(g);
    }
}