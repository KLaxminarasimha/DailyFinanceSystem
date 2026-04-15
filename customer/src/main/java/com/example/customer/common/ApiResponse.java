package com.example.customer.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;
    private int statusCode;

    //  success
    public static <T> ApiResponse<T> success(T data, String message, int statusCode) {
        return new ApiResponse<>(true, message, data, LocalDateTime.now(), statusCode);
    }

    // failure
    public static <T> ApiResponse<T> failure(String message, int statusCode) {
        return new ApiResponse<>(false, message, null, LocalDateTime.now(), statusCode);
    }

    // NEW METHOD
    public static <T> ApiResponse<T> failure(String message, int statusCode, T data) {
        return new ApiResponse<>(false, message, data, LocalDateTime.now(), statusCode);
    }
}