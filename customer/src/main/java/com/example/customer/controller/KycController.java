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

    @PostMapping("/{id}/kyc")
    public Kyc submitKyc(@PathVariable Long id, @RequestBody KycDTO dto) {
        return kycService.submitKyc(id, dto);
    }

    @PostMapping("/{id}/kyc/verify")
    public String verifyOtp(@PathVariable Long id,
                            @RequestBody OtpDTO dto) {
        return kycService.verifyOtp(id, dto.getOtp());
    }
}