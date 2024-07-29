package com.project.retailhub.data.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "receiving_details")
public class ReceivingDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int receivingDetailId;
    private BigDecimal quantity;
    private BigDecimal receivingCost;
    private String note;

    @ManyToOne
    @JoinColumn(name = "receiving_id")
    private Receiving receiving;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products product;

}
