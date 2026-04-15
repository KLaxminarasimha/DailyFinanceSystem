package com.example.customer.entity;

import com.example.customer.common.BaseEntity;
import com.example.customer.enums.KycStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    private String name;

    @Column(unique = true, nullable = false)
    private String phone;

    @Column(unique = true)
    private String email;

    @Column(unique = true, nullable = false)
    private String aadhar;

    @Column(unique = true, nullable = false)
    private String pan;

    private Double income;

    private String address;

    @Enumerated(EnumType.STRING)
    private KycStatus kycStatus = KycStatus.PENDING;

    // ✅ NEW FILE FIELDS
    private String aadharFile;
    private String panFile;
    private String signature;
    private String selfie;

    //  RELATIONSHIP
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Guarantor> guarantors;
}