package com.example.customer.controller;

import com.example.customer.dto.GuarantorDTO;
import com.example.customer.entity.Guarantor;
import com.example.customer.service.GuarantorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class GuarantorController {

    private final GuarantorService guarantorService;

    @PostMapping("/guarantor")
    public Guarantor addGuarantor(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestBody GuarantorDTO dto
    ) {
        return guarantorService.addGuarantorByUserId(userId, dto);
    }
}