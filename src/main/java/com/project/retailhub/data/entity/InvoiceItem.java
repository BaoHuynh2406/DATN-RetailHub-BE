package com.project.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invoice_items")
public class InvoiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invoiceItemId;

    private Integer quantity;

    private BigDecimal cost;

    private BigDecimal unitPrice;

    private Double discountRate;

    private double taxRate;

    // Khóa ngoại
    private Long invoiceId;

    private Long productId;

}
