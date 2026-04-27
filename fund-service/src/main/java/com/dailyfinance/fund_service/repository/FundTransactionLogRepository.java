package com.dailyfinance.fund_service.repository;

import com.dailyfinance.fund_service.entity.FundTransactionLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FundTransactionLogRepository extends JpaRepository<FundTransactionLog, Long> {
}
