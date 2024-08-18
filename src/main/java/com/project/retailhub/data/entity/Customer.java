package com.project.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "customers")
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    private String fullName;

    private String phoneNumber;

    private int points;

    private Boolean isActive;

    private Boolean isDelete;

    @OneToMany(mappedBy = "customer")
    private List<PointHistory> pointHistories;

    @OneToMany(mappedBy = "customer")
    private List<Invoice> invoices;


}
