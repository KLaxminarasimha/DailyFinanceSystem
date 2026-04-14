package com.example.customer.repository;

import com.example.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    //  FIND METHODS
    Optional<Customer> findByPhone(String phone);

    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByAadhar(String aadhar);

    Optional<Customer> findByPan(String pan);


    // EXISTS METHODS (FOR DUPLICATE CHECKS)
    boolean existsByPhone(String phone);

    boolean existsByEmail(String email);

    boolean existsByAadhar(String aadhar);

    boolean existsByPan(String pan);
}