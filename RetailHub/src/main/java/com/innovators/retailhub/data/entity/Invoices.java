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
    private int employeeId;
    private Date invoiceDate;
    private Double totalTax;
    private Double totalAmount;
    private Double totalPayment;
}
