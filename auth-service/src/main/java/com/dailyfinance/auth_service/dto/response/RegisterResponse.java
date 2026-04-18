package com.dailyfinance.auth_service.dto.response;

import com.dailyfinance.auth_service.entity.Role;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class RegisterResponse {
    private Long userId;
    private String email;
    private String name;
    private String phone;
    private Role role;
    private String agentArea;
    private Instant createdAt;

}