package com.uniquehire.paymentservice.utils;

import com.uniquehire.paymentservice.dtos.Response.ApiResponse;

import java.time.LocalDateTime;

public final class ResponseUtil {
    private ResponseUtil() {
    }

    public static <T> ApiResponse<T> success(String message, T data, int statusCode) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setMessage(message);
        response.setData(data);
        response.setTimestamp(LocalDateTime.now());
        response.setStatusCode(statusCode);
        return response;
    }

    public static ApiResponse<Object> error(String message, int statusCode) {
        ApiResponse<Object> response = new ApiResponse<>();
        response.setSuccess(false);
        response.setMessage(message);
        response.setData(null);
        response.setTimestamp(LocalDateTime.now());
        response.setStatusCode(statusCode);
        return response;
    }
}
