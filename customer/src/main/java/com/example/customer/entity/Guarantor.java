package com.example.customer.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "guarantor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Guarantor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phone;
    private String email;
    private String pan;
    private String relation;
    private String address;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
}