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
@Table(name = "stock_check_item")
public class StockCheckItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockCheckItemId;

    private BigDecimal actualQuantity;

    private BigDecimal recordedQuantity;

    @Column(name = "note", columnDefinition = "NVARCHAR(50)")
    private String note;

    private Long stockCheckId;

    private Long productId;
}
