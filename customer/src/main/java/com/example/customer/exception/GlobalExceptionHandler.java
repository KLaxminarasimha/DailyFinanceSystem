package com.example.customer.exception;

import com.example.customer.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 🔴 NOT FOUND
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleNotFound(ResourceNotFoundException ex) {

        return new ResponseEntity<>(
                ApiResponse.failure(ex.getMessage(), 404),
                HttpStatus.NOT_FOUND
        );
    }

    // 🔴 DUPLICATE
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiResponse<?>> handleDuplicate(DuplicateResourceException ex) {

        return new ResponseEntity<>(
                ApiResponse.failure(ex.getMessage(), 409),
                HttpStatus.CONFLICT
        );
    }

    // 🔴 BAD REQUEST
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<?>> handleBadRequest(BadRequestException ex) {

        return new ResponseEntity<>(
                ApiResponse.failure(ex.getMessage(), 400),
                HttpStatus.BAD_REQUEST
        );
    }

    // 🔴 GENERIC
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGeneric(Exception ex) {

        return new ResponseEntity<>(
                ApiResponse.failure("Something went wrong", 500),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}