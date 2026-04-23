package com.example.customer.entity;

import com.example.customer.enums.Gender;
import com.example.customer.enums.KycStatus;
import com.example.customer.enums.UserType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;

    private String address;
    private String pincode;

    private LocalDate dob;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private KycStatus kycStatus;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // ✅ Optional (only if you want to fetch guarantors with customer)
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Guarantor> guarantors;
}