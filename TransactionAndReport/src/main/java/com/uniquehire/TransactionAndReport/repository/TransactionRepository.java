package com.uniquehire.TransactionAndReport.repository;

import com.uniquehire.TransactionAndReport.entity.Transaction;
import com.uniquehire.TransactionAndReport.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByPaymentId(Long paymentId);
    List<Transaction> findByStatus(TransactionStatus status);
    List<Transaction> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
    List<Transaction> findByLoanId(Long loanId);
    List<Transaction> findByAgentId(Long agentId);
    List<Transaction> findByCustomerId(Long customerId);
    List<Transaction> findByStatusAndTimestampBetween(
            TransactionStatus status,
            LocalDateTime start,
            LocalDateTime end
    );

    Long countByStatus(TransactionStatus status);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.status = :status")
    BigDecimal sumAmountByStatus(TransactionStatus status);

    @Query("""
    SELECT t.agentId, SUM(t.amount) as total
    FROM Transaction t
    WHERE t.status = 'SUCCESS'
    GROUP BY t.agentId
    ORDER BY total DESC
    LIMIT 1
    """)
    Object findTopAgentByCollection();
}
