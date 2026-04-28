package com.uniquehire.TransactionAndReport.repository;

import com.uniquehire.TransactionAndReport.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByReferenceId(Long referenceId);
}