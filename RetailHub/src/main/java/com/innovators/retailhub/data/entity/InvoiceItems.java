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
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    @Column(name = "unit_price", nullable = false)
    private Double unitPrice;
    @Column(name = "tax_amount", nullable = false)
    private Double taxAmount;

    // Khóa ngoại
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products product;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoices invoicesItems;
}
