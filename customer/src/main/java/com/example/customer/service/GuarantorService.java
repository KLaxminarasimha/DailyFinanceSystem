package com.example.customer.service;

import com.example.customer.dto.CreateGuarantorRequest;
import com.example.customer.dto.GuarantorResponse;

import java.util.List;

public interface GuarantorService {

    GuarantorResponse addGuarantor(Long customerId, CreateGuarantorRequest request);

    List<GuarantorResponse> getGuarantorsByCustomer(Long customerId);
}