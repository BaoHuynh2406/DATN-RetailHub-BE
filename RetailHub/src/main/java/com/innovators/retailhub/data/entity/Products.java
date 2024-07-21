package com.innovators.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Table(name = "products")
@Data
public class Products
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;
    private String barcode;
    private String productName;
    private String productDescription;
    private String image;
    private Double cost;
    private Double quantity;
    private String unit;
    private String location;
    private Date expiryDate;
    private Boolean status;
}
