
package com.example.customer.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employee_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;
    private String employeeId;
    private Double monthlyIncome;
    private Integer experience;

    @OneToOne
    @JoinColumn(name = "customer_id", nullable = false, unique = true)
    private Customer customer;
}