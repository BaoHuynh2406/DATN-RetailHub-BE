package com.innovators.retailhub.data.entity;
import jakarta.persistence.*;
import lombok.Data;

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
    private Double quantity;
    private Double receivingCost;
    private String note;
}
