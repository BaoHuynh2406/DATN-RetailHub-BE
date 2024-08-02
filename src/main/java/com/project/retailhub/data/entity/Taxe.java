package com.project.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@Table(name = "taxes")
public class Taxe
{
    @Id
    @Column(name = "tax_id", length = 10)
    private String taxId;
    private String taxName;
    private BigDecimal taxRate;

    @OneToMany(mappedBy = "tax")
    private List<Product> products;
}
