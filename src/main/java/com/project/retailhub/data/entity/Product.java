package com.project.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "products")
@Data
public class Product
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String barcode;

    private String productName;

    private String productDescription;

    private String image;

    private BigDecimal cost;

    private BigDecimal price;

    private BigDecimal inventoryCount;

    private String unit;

    private String location;

    private Date expiryDate;

    private Boolean isDelete;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "tax_id")
    private Taxe tax;

    @OneToMany(mappedBy = "product")
    private List<ReceivingDetail> receivingDetails;

    @OneToMany(mappedBy = "product")
    private List<InvoiceItem> invoiceItems;

    @OneToMany(mappedBy = "product")
    private List<StockCheckItem> stockCheckItems;


}
