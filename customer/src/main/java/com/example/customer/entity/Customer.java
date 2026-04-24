package com.example.customer.entity;

import com.example.customer.enums.UserType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long authUserId;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    private LocalDate dob;
    private String gender;

    private String address;
    private String city;
    private String state;
    private String pincode;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    // 🔥 ADD THESE (VERY IMPORTANT)

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private EmployeeDetails employeeDetails;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private BusinessDetails businessDetails;
}