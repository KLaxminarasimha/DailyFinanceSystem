package com.example.customer.common;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;
    private int statusCode;

    // ✅ SUCCESS RESPONSE
    public static <T> ApiResponse<T> success(T data, String message, int statusCode) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .statusCode(statusCode)
                .build();
    }

    // ✅ FAILURE RESPONSE (NO DATA)
    public static <T> ApiResponse<T> failure(String message, int statusCode) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .data(null)
                .timestamp(LocalDateTime.now())
                .statusCode(statusCode)
                .build();
    }

    // ✅ FAILURE RESPONSE (WITH DATA)
    public static <T> ApiResponse<T> failure(String message, int statusCode, T data) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .statusCode(statusCode)
                .build();
    }
}