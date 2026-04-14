package com.example.customer.serviceImpl;

import com.example.customer.dto.CreateGuarantorRequest;
import com.example.customer.dto.GuarantorResponse;
import com.example.customer.entity.Customer;
import com.example.customer.entity.Guarantor;
import com.example.customer.exception.ResourceNotFoundException;
import com.example.customer.mapper.GuarantorMapper;
import com.example.customer.repository.CustomerRepository;
import com.example.customer.repository.GuarantorRepository;
import com.example.customer.service.GuarantorService;
import com.example.customer.common.constants.AppConstants;
import com.example.customer.common.validation.ValidationUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GuarantorServiceImpl implements GuarantorService {

    private final GuarantorRepository guarantorRepository;
    private final CustomerRepository customerRepository;
    private final ValidationUtil validationUtil;

    //  ADD GUARANTOR
    @Override
    public GuarantorResponse addGuarantor(Long customerId, CreateGuarantorRequest request) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(AppConstants.CUSTOMER_NOT_FOUND));

        int currentCount = customer.getGuarantors() != null
                ? customer.getGuarantors().size()
                : 0;

        //  VALIDATION (already using constants internally)
        validationUtil.validateGuarantor(
                request.getPhone(),
                customerId,
                currentCount
        );

        //  MAPPER
        Guarantor guarantor = GuarantorMapper.toEntity(request);

        // RELATIONSHIP FIX (CRITICAL)
        guarantor.setCustomer(customer);

        if (customer.getGuarantors() == null) {
            customer.setGuarantors(new ArrayList<>());
        }

        customer.getGuarantors().add(guarantor);

        customerRepository.save(customer);

        return GuarantorMapper.toResponse(guarantor);
    }

    //  GET GUARANTORS
    @Override
    @Transactional(readOnly = true)
    public List<GuarantorResponse> getGuarantorsByCustomer(Long customerId) {

        // 🔥 already using AppConstants inside ValidationUtil
        validationUtil.validateCustomerExists(customerId);

        List<Guarantor> guarantors = guarantorRepository.findByCustomerCustomerId(customerId);

        List<GuarantorResponse> responseList = new ArrayList<>();

        for (Guarantor g : guarantors) {
            responseList.add(GuarantorMapper.toResponse(g));
        }

        return responseList;
    }
}