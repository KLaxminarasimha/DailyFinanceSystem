
package com.example.customer.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "business_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String businessName;
    private String businessType;
    private String gstNumber;
    private Double monthlyIncome;
    private Integer yearsInBusiness;

    @OneToOne
    @JoinColumn(name = "customer_id", nullable = false, unique = true)
    private Customer customer;
}