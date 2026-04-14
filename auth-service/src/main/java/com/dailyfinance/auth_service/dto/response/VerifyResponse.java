package com.dailyfinance.auth_service.dto.response;

import com.dailyfinance.auth_service.entity.Role;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerifyResponse {

    private Long userId;
    private String email;
    private Role role;
    private boolean isValid;
}