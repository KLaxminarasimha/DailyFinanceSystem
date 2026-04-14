package com.example.customer.entity;

import com.example.customer.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "guarantors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Guarantor extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long guarantorId;

    private String name;

    private String phone;

    private String relationship;

    private Boolean verified = false;

    // FK (read-only)
    @Column(name = "customer_id", insertable = false, updatable = false)
    private Long customerId;



    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}