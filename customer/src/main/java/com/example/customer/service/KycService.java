package com.example.customer.service;

import com.example.customer.dto.KycDTO;
import com.example.customer.entity.Kyc;

public interface KycService {

        Kyc submitKycByUserId(Long userId, KycDTO dto);

        String verifyOtpByUserId(Long userId, String otp);
}