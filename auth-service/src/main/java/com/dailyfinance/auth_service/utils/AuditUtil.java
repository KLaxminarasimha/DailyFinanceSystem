package com.dailyfinance.auth_service.utils;

import com.dailyfinance.auth_service.entity.AuditLog;
import com.dailyfinance.auth_service.entity.User;

public class AuditUtil {

    public static AuditLog createLog(User user, String action, String entity) {
        return AuditLog.builder()
                .user(user)
                .action(action)
                .entity(entity)
                .build();
    }
}