package com.example.customer.repository;

import com.example.customer.entity.EmployeeDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<EmployeeDetails, Long> {
}