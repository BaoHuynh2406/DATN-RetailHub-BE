package com.innovators.retailhub.data.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Data
@Table(name = "receving_details")
public class RecvingDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int recevingDetaiId;
    private int recevingId;
    private int productId;
    private Double quantity;
    private Double racevingCost;
    private String note;
}
