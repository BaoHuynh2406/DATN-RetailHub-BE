package com.innovators.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name ="Customers")
@Data
public class Customers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private int customerId;
    @Column(name = "full_name", nullable = false, length = 50)
    private String fullName;
    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;
    @Column(name = "points", nullable = false)
    private int points;
}
