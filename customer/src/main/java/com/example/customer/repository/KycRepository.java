package com.example.customer.repository;

import com.example.customer.entity.Customer;
import com.example.customer.entity.Kyc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KycRepository extends JpaRepository<Kyc, Long> {
    Optional<Kyc> findByCustomer(Customer customer);
}