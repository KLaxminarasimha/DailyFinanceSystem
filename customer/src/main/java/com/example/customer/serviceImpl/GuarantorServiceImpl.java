package com.example.customer.serviceImpl;

import com.example.customer.dto.CreateGuarantorRequest;
import com.example.customer.dto.GuarantorResponse;
import com.example.customer.entity.Customer;
import com.example.customer.entity.Guarantor;
import com.example.customer.mapper.GuarantorMapper;
import com.example.customer.repository.GuarantorRepository;
import com.example.customer.service.GuarantorService;
import com.example.customer.common.validation.ValidationUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GuarantorServiceImpl implements GuarantorService {

    private final GuarantorRepository guarantorRepository;
    private final ValidationUtil validationUtil;

    // ✅ ADD GUARANTOR
    @Override
    public GuarantorResponse addGuarantor(Long customerId, CreateGuarantorRequest request) {

        // 🔥 Step 1: Get customer
        Customer customer = validationUtil.getCustomerOrThrow(customerId);

        // 🔥 Step 2: Count guarantors
        int currentCount = guarantorRepository.countByCustomerCustomerId(customerId);

        // 🔥 Step 3: Validate (UPDATED with email)
        validationUtil.validateGuarantor(
                request.getPhone(),
                request.getEmail(), // ✅ ADD THIS
                customerId,
                currentCount
        );

        // 🔥 Step 4: Map
        Guarantor guarantor = GuarantorMapper.toEntity(request);
        guarantor.setCustomer(customer);

        // 🔥 Step 5: Audit fields
        guarantor.setCreatedAt(LocalDateTime.now());
        guarantor.setUpdatedAt(LocalDateTime.now());

        // 🔥 Step 6: Save
        guarantorRepository.save(guarantor);

        return GuarantorMapper.toResponse(guarantor);
    }

    // ✅ GET GUARANTORS
    @Override
    @Transactional(readOnly = true)
    public List<GuarantorResponse> getGuarantorsByCustomer(Long customerId) {

        validationUtil.validateCustomerExists(customerId);

        List<Guarantor> guarantors =
                guarantorRepository.findByCustomerCustomerId(customerId);

        List<GuarantorResponse> responseList = new ArrayList<>();

        for (Guarantor g : guarantors) {
            responseList.add(GuarantorMapper.toResponse(g));
        }

        return responseList;
    }
}