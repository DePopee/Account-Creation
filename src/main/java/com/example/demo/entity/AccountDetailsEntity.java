package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "account_details")
@AllArgsConstructor
@NoArgsConstructor
@Data

public class AccountDetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "customer_id")
    private CustomerDetailsEntity customer;

    @Column(unique = true, nullable = false)
    private String accountNumber;

}
