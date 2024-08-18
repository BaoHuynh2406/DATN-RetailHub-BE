package com.project.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name ="invoices")
@Data
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invoiceId;

    private Date invoiceDate;

    private BigDecimal totalTax;

    private BigDecimal totalAmount;

    private BigDecimal totalPayment;

    // Khóa ngoại
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "invoice")
    private List<InvoiceItem> invoiceItems;

    @OneToMany(mappedBy = "invoice")
    private List<Payment> payments;
}
