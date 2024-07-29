package com.project.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "suppliers")
@Data
public class Suppliers
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int supplierId;
    private String supplierName;
    private String supplierDescription;
    private String supplierPhoneNumber;
    private String supplierEmail;
    private String supplierAddress;
    private boolean active;

    // Khóa ngoại
    @OneToMany(mappedBy = "supplier")
    private List<Receiving> receivings;
}
