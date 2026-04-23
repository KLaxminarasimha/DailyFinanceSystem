package com.example.customer.dto;

import lombok.Data;
import java.time.LocalDateTime;

<<<<<<< HEAD
    @Data
    public class GuarantorResponse {

        private Long guarantorId;
        private String name;
        private String phone;
        private String relationship;
        private Boolean verified;
        private Long customerId;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
=======
@Data
public class GuarantorResponse {
>>>>>>> 5e9fb6de4200877668488404aff1537a8fafd0c6

    private Long guarantorId;
    private String name;
    private String phone;
    private String email;
    private String relationship;
    private Boolean verified;
    private Long customerId;
    private String panNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}