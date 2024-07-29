package com.project.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "products")
@Data
public class Products
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "barcode", nullable = false, length = 25)
    private String barcode;

    @Column(name = "product_name", nullable = false, length = 50)
    private String productName;

    @Column(name = "product_description", nullable = false, length = 500)
    private String productDescription;

    @Column(name = "image", nullable = false, length = 500)
    private String image;

    @Column(name = "cost", nullable = false, precision = 18, scale = 2)
    private BigDecimal cost;

    @Column(name = "quantity", nullable = false, precision = 10, scale = 2)
    private BigDecimal quantity;

    @Column(name = "unit", nullable = false, length = 10)
    private String unit;

    @Column(name = "location", length = 255)
    private String location;

    @Column(name = "expiry_date", nullable = false)
    private Date expiryDate;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Categories category;

    @ManyToOne
    @JoinColumn(name = "tax_id")
    private Taxes tax;

    @OneToMany(mappedBy = "product")
    private List<ReceivingDetails> receivingDetails;

    @OneToMany(mappedBy = "product")
    private List<InvoiceItems> invoiceItems;

    @OneToMany(mappedBy = "product")
    private List<StockCheckItem> stockCheckItems;


}
