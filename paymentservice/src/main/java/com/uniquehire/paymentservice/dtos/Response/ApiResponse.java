package com.uniquehire.paymentservice.dtos.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;
    private int statusCode;

//    // ✅ SUCCESS RESPONSE
//    public static <T> ApiResponse<T> success(String message, T data, int statusCode) {
//        return new ApiResponse<>(
//                true,
//                message,
//                data,
//                LocalDateTime.now(),
//                statusCode
//        );
//    }
//
//    // ✅ ERROR RESPONSE (THIS WAS MISSING)
//    public static <T> ApiResponse<T> error(String message, int statusCode) {
//        return new ApiResponse<>(
//                false,
//                message,
//                null,
//                LocalDateTime.now(),
//                statusCode
//        );
//    }

}
