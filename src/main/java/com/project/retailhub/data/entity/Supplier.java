package com.project.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "suppliers")
@Data
public class Supplier
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplierId;
    private String supplierName;
    private String supplierDescription;
    private String supplierPhoneNumber;
    private String supplierEmail;
    private String supplierAddress;
    private boolean isDelete;

    // Khóa ngoại
    @OneToMany(mappedBy = "supplier")
    private List<Receiving> receivings;
}
