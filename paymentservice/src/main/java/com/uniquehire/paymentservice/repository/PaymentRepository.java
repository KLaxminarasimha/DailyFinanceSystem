package com.uniquehire.paymentservice.repository;

import com.uniquehire.paymentservice.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    //Give me all payments for this loanId, sorted by payment date from oldest to latest
    List<Payment> findByLoanIdOrderByPaymentDateAsc(Long loanId);
    List<Payment> findByIsDeletedFalse();
    List<Payment> findByLoanIdAndIsDeletedFalse(Long loanId);
}
