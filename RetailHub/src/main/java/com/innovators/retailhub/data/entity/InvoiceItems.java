package com.innovators.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "invoice_items")
public class InvoiceItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_item_id")
    private Integer invoiceItemId;

    @Column(name = "invoice_id", nullable = false)
    private Integer invoiceId;

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false, precision = 18, scale = 2)
    private Double unitPrice;

    @Column(name = "tax_amount", nullable = false, precision = 10, scale = 2)
    private Double taxAmount;

    // Nếu có quan hệ với các entity khác thì cần thêm @ManyToOne, @OneToMany, @JoinColumn, v.v...
}
