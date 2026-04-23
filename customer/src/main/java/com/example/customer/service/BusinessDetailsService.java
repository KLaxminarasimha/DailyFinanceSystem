package com.example.customer.service;

import com.example.customer.dto.BusinessDTO;
import com.example.customer.entity.BusinessDetails;

public interface BusinessDetailsService {

    BusinessDetails addBusiness(Long customerId, BusinessDTO dto);
}