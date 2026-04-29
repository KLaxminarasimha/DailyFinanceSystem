package com.example.customer.service;

import com.example.customer.dto.BusinessDTO;
import com.example.customer.entity.BusinessDetails;

public interface BusinessDetailsService {
    BusinessDetails addBusinessByUserId(Long userId, BusinessDTO dto);
}