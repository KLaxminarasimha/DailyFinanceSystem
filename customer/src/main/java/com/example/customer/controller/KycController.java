package com.example.customer.controller;

import com.example.customer.dto.KycDTO;
import com.example.customer.dto.OtpDTO;
import com.example.customer.entity.Kyc;
import com.example.customer.service.KycService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class KycController {

    private final KycService kycService;

    @PostMapping("/kyc")
    public Kyc submitKyc(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestBody KycDTO dto
    ) {
        return kycService.submitKycByUserId(userId, dto);
    }

    @PostMapping("/kyc/verify")
    public String verifyOtp(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestBody OtpDTO dto) {

        return kycService.verifyOtpByUserId(userId, dto.getOtp());
    }
}