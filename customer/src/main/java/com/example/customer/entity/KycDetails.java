package com.example.customer.entity;

import com.example.customer.enums.KycStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "kyc_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KycDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String aadhar;
    private String pan;
    private String phone;

    @Enumerated(EnumType.STRING)
    private KycStatus status;   //  ADD THIS

    private String rejectionReason; //  OPTIONAL

    @OneToOne
    @JoinColumn(name = "customer_id", nullable = false, unique = true)
    private Customer customer;
}