package com.dailyfinance.auth_service.repository;

import com.dailyfinance.auth_service.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog,Long> {
}
