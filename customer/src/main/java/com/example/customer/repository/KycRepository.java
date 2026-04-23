package com.example.customer.repository;

import com.example.customer.entity.Kyc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KycRepository extends JpaRepository<Kyc, Long> {
}