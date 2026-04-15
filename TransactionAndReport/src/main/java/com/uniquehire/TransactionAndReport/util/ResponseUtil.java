package com.uniquehire.TransactionAndReport.util;

import com.uniquehire.TransactionAndReport.dto.ApiResponseDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class ResponseUtil {

    public static <T> ResponseEntity<ApiResponseDto<T>> success(T data, String message) {

        ApiResponseDto<T> response = new ApiResponseDto<>();

        response.setSuccess(true);
        response.setMessage(message);
        response.setData(data);
        response.setTimestamp(LocalDateTime.now());
        response.setStatusCode(HttpStatus.OK.value());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public static ResponseEntity<ApiResponseDto<Object>> error(String message, HttpStatus status) {

        ApiResponseDto<Object> response = new ApiResponseDto<>();

        response.setSuccess(false);
        response.setMessage(message);
        response.setTimestamp(LocalDateTime.now());
        response.setStatusCode(status.value());

        return new ResponseEntity<>(response, status);
    }
}
