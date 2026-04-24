package com.example.customer.repository;

import com.example.customer.entity.BusinessDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessRepository extends JpaRepository<BusinessDetails, Long> {
}