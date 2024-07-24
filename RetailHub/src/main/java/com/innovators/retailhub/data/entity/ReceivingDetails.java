package com.innovators.retailhub.data.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.awt.*;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "receiving_details")
public class ReceivingDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int receivingDetailId;

    private int receivingId;

    private int productId;

    private BigDecimal quantity;

    private BigDecimal receivingCost;

    private String note;

    @ManyToOne
    @JoinColumn(name = "receving_id")
    private Receiving receiving;

    @OneToMany(mappedBy = "receving_id")
    private List<Receiving> receiving;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Products product;
}
