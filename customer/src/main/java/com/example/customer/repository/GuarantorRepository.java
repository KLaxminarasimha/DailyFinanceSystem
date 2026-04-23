package com.example.customer.repository;

import com.example.customer.entity.Guarantor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuarantorRepository extends JpaRepository<Guarantor, Long> {
}