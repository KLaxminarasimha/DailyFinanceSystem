package com.example.customer.entity;

import com.example.customer.enums.KycStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "kyc")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Kyc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String aadhar;
    private String pan;
    private String email;
    private String phone;
    private String ifsc;
    private String accountNumber;

    private String otp;
    private LocalDateTime otpExpiry;

    @Enumerated(EnumType.STRING)
    private KycStatus kycStatus;

    @OneToOne
    @JoinColumn(name = "customer_id", unique = true)
    private Customer customer;
}