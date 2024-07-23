package com.innovators.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name ="customers")
@Data
public class Customers
{
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

    @OneToMany(mappedBy = "customers")
    private List<Invoices> invoices;

    @OneToMany(mappedBy = "pointHistory")
    private List<PointHistory> pointHistories;
}
