package com.example.customer.repository;

import com.example.customer.entity.EmployeeDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeDetailsRepository extends JpaRepository<EmployeeDetails, Long> {

    Optional<EmployeeDetails> findByCustomerCustomerId(Long customerId);

    boolean existsByCustomerCustomerId(Long customerId);
}