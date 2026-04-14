package com.dailyfinance.auth_service.dto.response;

import com.dailyfinance.auth_service.entity.Role;

public class AuthResponse {
    private Long userId;
    private String email;
    private String name;
    private Role role;
    private String token;
    private Long expiresIn;
}

