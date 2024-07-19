package com.innovators.retailhub.data.entity;
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

    private int receivingId;

    private int productId;

    private BigDecimal quantity;

    private BigDecimal receivingCost;

    private String note;
}
