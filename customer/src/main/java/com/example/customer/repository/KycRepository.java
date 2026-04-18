package com.example.customer.repository;

import com.example.customer.entity.KycDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KycRepository extends JpaRepository<KycDetails, Long> {

    Optional<KycDetails> findByCustomerCustomerId(Long customerId);

    boolean existsByAadhar(String aadhar);
    boolean existsByPan(String pan);
}