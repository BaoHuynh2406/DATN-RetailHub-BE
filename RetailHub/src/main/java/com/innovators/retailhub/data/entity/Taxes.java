package com.innovators.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "taxes")
public class Taxes
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String taxId;
    private String taxName;
    private Double taxRate;
}
