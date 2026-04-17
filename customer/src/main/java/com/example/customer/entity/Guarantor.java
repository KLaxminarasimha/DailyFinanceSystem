package com.example.customer.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "guarantors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Guarantor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long guarantorId;

    private String name;
    private String phone;
    private String relationship;
    private String email;

    private Boolean verified = false;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
}