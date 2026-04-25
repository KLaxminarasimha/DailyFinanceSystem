package com.dailyfinance.fund_service.repository;

import com.dailyfinance.fund_service.entity.Fund;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FundRepository extends JpaRepository<Fund, Long> {
}
