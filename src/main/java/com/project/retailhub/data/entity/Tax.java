package com.project.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@Table(name = "taxes")
public class Tax {

    @Id
    @Column(name = "tax_id", length = 10)
    private String taxId;

    @Column(name = "tax_name", columnDefinition = "NVARCHAR(50)")
    private String taxName;

    @Column(name = "tax_rate", precision = 5, scale = 2)
    private BigDecimal taxRate;

    @OneToMany(mappedBy = "tax")
    private List<Product> products;
}
