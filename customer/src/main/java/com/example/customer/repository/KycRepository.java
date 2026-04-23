package com.example.customer.repository;

import com.example.customer.entity.KycDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KycRepository extends JpaRepository<KycDetails, Long> {

    Optional<KycDetails> findByCustomerCustomerId(Long customerId);

    boolean existsByAadhar(String aadhar);

    // ✅ FIX: Case-insensitive PAN check
    boolean existsByPanNumberIgnoreCase(String panNumber);

    // ✅ NEW: Phone uniqueness
    boolean existsByPhone(String phone);
}