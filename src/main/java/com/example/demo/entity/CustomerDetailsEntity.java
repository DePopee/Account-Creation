package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "customer_details")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private String residentialAddress;

    @Column(unique = true, nullable = false)
    private String bvn;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "date_created", nullable = false)
    private LocalDate dateCreated;

    @Column(name = "time_created", nullable = false)
    private LocalTime timeCreated;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true,name = "customer_id")
    private String customerId;

}