package com.project.retailhub.data.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "receiving_details")
public class ReceivingDetail
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long receivingDetailId;
    private BigDecimal quantity;
    private BigDecimal receivingCost;
    private String note;

    @ManyToOne
    @JoinColumn(name = "receiving_id")
    private Receiving receiving;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
