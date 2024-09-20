package com.project.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "receiving_details")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceivingDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long receivingDetailId;

    @Column(name = "quantity", precision = 10, scale = 2, nullable = false)
    private BigDecimal quantity;

    @Column(name = "receiving_cost", precision = 10, scale = 2, nullable = false)
    private BigDecimal receivingCost;

    @Column(name = "note", columnDefinition = "NVARCHAR(50)")
    private String note;

    private Long receivingId;

    private Long productId;
}
