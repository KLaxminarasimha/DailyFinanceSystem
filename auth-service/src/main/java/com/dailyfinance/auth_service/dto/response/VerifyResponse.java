package com.dailyfinance.auth_service.dto.response;

import com.dailyfinance.auth_service.entity.Role;
import lombok.Builder;

@Builder
public class VerifyResponse {
    private Long userid;
    private String email;
    private Role role;
    private boolean isValid;
}
