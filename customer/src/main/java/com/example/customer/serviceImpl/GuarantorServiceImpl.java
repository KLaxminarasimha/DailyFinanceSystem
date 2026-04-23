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

        // 🔥 STEP 1: Get customer
        Customer customer = validationUtil.getCustomerOrThrow(customerId);

        // 🔥 STEP 2: Count existing guarantors
        int currentCount = guarantorRepository.countByCustomerCustomerId(customerId);

        // 🔥 STEP 3: Normalize PAN
        String pan = request.getPanNumber() != null
                ? request.getPanNumber().toUpperCase().trim()
                : null;

        // 🔥 STEP 4: Validate
        validationUtil.validateGuarantor(pan, customerId, currentCount);

        // 🔥 STEP 5: Map DTO → Entity
        Guarantor guarantor = GuarantorMapper.toEntity(request);

        // 🔥 Ensure normalized PAN is saved
        guarantor.setPanNumber(pan);

        // 🔥 Set relationship
        guarantor.setCustomer(customer);

        // 🔥 STEP 6: Audit fields
        guarantor.setCreatedAt(LocalDateTime.now());
        guarantor.setUpdatedAt(LocalDateTime.now());

        // 🔥 STEP 7: Save
        Guarantor savedGuarantor = guarantorRepository.save(guarantor);

        // 🔥 STEP 8: Return response
        return GuarantorMapper.toResponse(savedGuarantor);
    }

    // ✅ GET GUARANTORS BY CUSTOMER (WITHOUT STREAMS)
    @Override
    @Transactional(readOnly = true)
    public List<GuarantorResponse> getGuarantorsByCustomer(Long customerId) {

        // 🔥 Validate customer exists
        validationUtil.validateCustomerExists(customerId);

        // 🔥 Fetch guarantors
        List<Guarantor> guarantors =
                guarantorRepository.findByCustomerCustomerId(customerId);

        // 🔥 Convert to response list
        List<GuarantorResponse> responseList = new ArrayList<>();

        for (Guarantor g : guarantors) {
            responseList.add(GuarantorMapper.toResponse(g));
        }

        return responseList;
    }
}