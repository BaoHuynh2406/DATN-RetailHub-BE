package com.innovators.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "stock_check_item")
public class StockCheckItem
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_check_item_id")
    private Integer stockCheckItemId;
    @Column(name = "actual_quantity", nullable = false)
    private Double actualQuantity;
    @Column(name = "recorded_quantity", nullable = false)
    private Double recordedQuantity;
    @Column(name = "note", length = 50)
    private String note;

    // Khóa ngoại
    @ManyToOne
    @JoinColumn(name = "stock_check_id")
    private StockCheck stockCheck;


    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products product;

}
