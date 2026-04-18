package com.example.customer.repository;

import com.example.customer.entity.Guarantor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuarantorRepository extends JpaRepository<Guarantor, Long> {

    List<Guarantor> findByCustomerCustomerId(Long customerId);

    boolean existsByPhoneAndCustomerCustomerId(String phone, Long customerId);

    int countByCustomerCustomerId(Long customerId);

    boolean existsByEmailAndCustomerCustomerId(String email, Long customerId);
}