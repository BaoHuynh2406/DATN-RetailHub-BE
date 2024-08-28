package com.project.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "stock_check_item")
public class StockCheckItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockCheckItemId;

    private BigDecimal actualQuantity;

    private BigDecimal recordedQuantity;

    @Column(name = "note", columnDefinition = "NVARCHAR(50)")
    private String note;

    // Khóa ngoại
    @ManyToOne
    @JoinColumn(name = "stock_check_id")
    private StockCheck stockCheck;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
