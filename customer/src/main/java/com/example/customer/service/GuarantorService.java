package com.example.customer.service;

import com.example.customer.dto.GuarantorDTO;
import com.example.customer.entity.Guarantor;

public interface GuarantorService {
    Guarantor addGuarantorByUserId(Long userId, GuarantorDTO dto);
}