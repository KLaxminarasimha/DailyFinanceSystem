
package com.example.customer.service;

import com.example.customer.dto.BusinessDetailsRequest;
import com.example.customer.dto.BusinessDetailsResponse;

public interface BusinessDetailsService {

    BusinessDetailsResponse addBusinessDetails(Long customerId, BusinessDetailsRequest request);

    BusinessDetailsResponse getBusinessDetails(Long customerId);
}