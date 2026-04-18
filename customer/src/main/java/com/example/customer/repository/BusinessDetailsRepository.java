package com.example.customer.repository;

import com.example.customer.entity.BusinessDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusinessDetailsRepository extends JpaRepository<BusinessDetails, Long> {

    Optional<BusinessDetails> findByCustomerCustomerId(Long customerId);

    boolean existsByCustomerCustomerId(Long customerId);

    boolean existsByGstNumber(String gstNumber); // 🔥 optional but useful
}