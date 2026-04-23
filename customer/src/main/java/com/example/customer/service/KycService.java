package com.example.customer.service;

import com.example.customer.dto.KycDTO;
import com.example.customer.entity.Kyc;

public interface KycService {

        Kyc submitKyc(Long customerId, KycDTO dto);

        String verifyOtp(Long customerId, String otp);
}