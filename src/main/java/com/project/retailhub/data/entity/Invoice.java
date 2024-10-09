package com.project.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "invoices")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invoiceId;

    private Date invoiceDate;

    private BigDecimal totalTax;

    private BigDecimal totalAmount;

    private BigDecimal totalPayment;

    @Column(name = "status", length = 10)
    private String status;

    // Khóa ngoại
    private Long userId;

    private Long customerId;
}
