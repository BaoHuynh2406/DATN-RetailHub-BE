package com.innovators.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "stock_check_item")
public class StockCheckItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_check_item_id")
    private Integer stockCheckItemId;

    @Column(name = "stock_check_id", nullable = false)
    private Integer stockCheckId;

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Column(name = "actual_quantity", nullable = false, precision = 10, scale = 2)
    private Double actualQuantity;

    @Column(name = "recorded_quantity", nullable = false, precision = 10, scale = 2)
    private Double recordedQuantity;

    @Column(name = "note", length = 50)
    private String note;

    // Nếu có quan hệ với các entity khác thì cần thêm @ManyToOne, @OneToMany, @JoinColumn, v.v...
}
