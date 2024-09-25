package com.project.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "suppliers")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplierId;

    @Column(name = "supplier_name", columnDefinition = "NVARCHAR(50)", nullable = false)
    private String supplierName;

    @Column(name = "supplier_description", columnDefinition = "NVARCHAR(255)")
    private String supplierDescription;

    @Column(name = "supplier_phone_number", length = 15, nullable = false)
    private String supplierPhoneNumber;

    @Column(name = "supplier_email", length = 50, nullable = false)
    private String supplierEmail;

    @Column(name = "supplier_address", columnDefinition = "NVARCHAR(50)", nullable = false)
    private String supplierAddress;

    @Column(name = "is_delete", columnDefinition = "BIT DEFAULT 0", nullable = false)
    private boolean isDelete;

}
