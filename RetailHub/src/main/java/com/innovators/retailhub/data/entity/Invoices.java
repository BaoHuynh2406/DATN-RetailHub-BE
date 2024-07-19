package com.innovators.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name ="Invoices")
@Data
public class Invoices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int invoiceId;
    @Column(name = "employee_id", nullable = false)
    private int employeeId;
    @Column(name = "customer_id", nullable = false)
    private int customerId;
    @Column(name = "invoice_date", nullable = false)
    private Date invoiceDate;
    @Column(name = "total_tax", nullable = false)
    private Double totalTax;
    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;
    @Column(name = "total_payment", nullable = false)
    private Double totalPayment;
}
