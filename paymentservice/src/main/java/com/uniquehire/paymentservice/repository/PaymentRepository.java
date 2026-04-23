package com.uniquehire.paymentservice.repository;

import com.uniquehire.paymentservice.entity.Payment;
import com.uniquehire.paymentservice.enums.PaymentStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByLoanId(Long loanId);//get all payment for a loan

    List<Payment> findByStatus(Long loanId);// get payments by status

    List<Payment> findByLoanAndStatus(Long loanId, PaymentStatus status);// get payment by loan  +status




}
