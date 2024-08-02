package com.project.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Entity
@Table(name ="invoices")
@Data
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int invoiceId;
    @Column(name = "invoice_date", nullable = false)
    private Date invoiceDate;
    @Column(name = "total_tax", nullable = false)
    private Double totalTax;
    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;
    @Column(name = "total_payment", nullable = false)
    private Double totalPayment;

    // Khóa ngoại
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "invoice")
    private List<InvoiceItem> invoiceItems;

    @OneToMany(mappedBy = "invoice")
    private List<Payment> payments;
}
