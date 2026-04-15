package com.dailyfinance.auth_service.utils;
import com.dailyfinance.auth_service.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class ResponseUtil {

    // SUCCESS
    public static <T> ResponseEntity<ApiResponse<T>> success(T data, String message, int status) {
        return ResponseEntity.status(status).body(
                ApiResponse.<T>builder()
                        .success(true)
                        .message(message)
                        .data(data)
                        .timestamp(LocalDateTime.now())
                        .statusCode(status)
                        .build()
        );
    }

    // ERROR
    public static ResponseEntity<ApiResponse<Object>> error(String message, int status) {
        return ResponseEntity.status(status).body(
                ApiResponse.builder()
                        .success(false)
                        .message(message)
                        .data(null)
                        .timestamp(LocalDateTime.now())
                        .statusCode(status)
                        .build()
        );
    }
}
