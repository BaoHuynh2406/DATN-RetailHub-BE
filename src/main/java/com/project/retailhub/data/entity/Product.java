package com.project.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(name = "barcode", length = 25, nullable = false)
    private String barcode;

    @Column(name = "product_name", columnDefinition = "NVARCHAR(50)", nullable = false)
    private String productName;

    @Column(name = "product_description", columnDefinition = "NVARCHAR(500)", nullable = false)
    private String productDescription;

    @Column(name = "image", columnDefinition = "NVARCHAR(500)", nullable = false)
    private String image;

    @Column(name = "cost", precision = 18, scale = 2, nullable = false)
    private BigDecimal cost;

    @Column(name = "price", precision = 18, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(name = "inventory_count", precision = 10, scale = 2, nullable = false)
    private BigDecimal inventoryCount;

    @Column(name = "unit", columnDefinition = "NVARCHAR(10)", nullable = false)
    private String unit;

    @Column(name = "location", columnDefinition = "NVARCHAR(255)")
    private String location;

    @Column(name = "expiry_date", nullable = false)
    private Date expiryDate;

    @Column(name = "is_active", columnDefinition = "BIT DEFAULT 1", nullable = false)
    private Boolean isActive;

    @Column(name = "is_delete", columnDefinition = "BIT DEFAULT 0", nullable = false)
    private Boolean isDelete;

    // Khóa ngoại
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "tax_id")
    private Tax tax;

    @OneToMany(mappedBy = "product")
    private List<ReceivingDetail> receivingDetails;

    @OneToMany(mappedBy = "product")
    private List<InvoiceItem> invoiceItems;

    @OneToMany(mappedBy = "product")
    private List<StockCheckItem> stockCheckItems;
}
