package com.innovators.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name ="Customers")
@Data
public class Customers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customersId;
    private String fullName;
    private String phoneNumber;
    private int points;
}
