package com.uniquehire.paymentservice.repository;

import com.uniquehire.paymentservice.entity.Fine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FineRepository extends JpaRepository<Fine,Long > {
    List<Fine> findByLoanIdOrderByFineDateAsc(Long loanId);
}
