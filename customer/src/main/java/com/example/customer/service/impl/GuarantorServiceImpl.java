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
    public Guarantor addGuarantor(Long customerId, GuarantorDTO dto) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // ❌ Guarantor should NOT be a registered customer
        if (customerRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Guarantor cannot be a registered customer");
        }

        Guarantor guarantor = new Guarantor();
        guarantor.setName(dto.getName());
        guarantor.setPhone(dto.getPhone());
        guarantor.setEmail(dto.getEmail());
        guarantor.setPan(dto.getPan());
        guarantor.setRelation(dto.getRelation());
        guarantor.setAddress(dto.getAddress());
        guarantor.setCustomer(customer);

        return guarantorRepository.save(guarantor);
    }
}