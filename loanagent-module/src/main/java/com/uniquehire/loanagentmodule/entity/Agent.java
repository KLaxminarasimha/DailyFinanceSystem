package com.uniquehire.loanagentmodule.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name="agents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Agent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long agentId;

    @Column(nullable=false,length=100)
    private String name;

    @Column(nullable=false,unique=true,length=10)
    private String phone;

    @Column(length=100)
    private String email;

    @Column(length=100)
    private String area;

    @Column(nullable=false)
    private Integer assignedLoans=0;

    @Column(precision=12,scale=2)
    private BigDecimal collectionTarget;

    @Column(precision=5,scale=2)
    private BigDecimal commissionRate;

    @OneToMany(mappedBy="agent")
    private List<Loan> loans;
}
