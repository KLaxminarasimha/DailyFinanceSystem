package com.uniquehire.TransactionAndReport.exception;

import com.uniquehire.TransactionAndReport.constants.MessageConstants;
import com.uniquehire.TransactionAndReport.dto.ApiResponseDto;
import com.uniquehire.TransactionAndReport.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseDto<Object>> handleNotFound(ResourceNotFoundException ex) {
        return ResponseUtil.error(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiResponseDto<Object>> handleValidation(ValidationException ex) {
        return ResponseUtil.error(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponseDto<Object>> handleBusiness(BusinessException ex) {
        return ResponseUtil.error(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponseDto<Object>> handleUnauthorized(UnauthorizedException ex) {
        return ResponseUtil.error(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDto<Object>> handleGeneric(Exception ex) {
        return ResponseUtil.error(MessageConstants.TRANSACTION_FAILED, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}