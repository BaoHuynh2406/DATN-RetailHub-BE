package com.project.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "taxes")
public class Tax {

    @Id
    @Column(name = "tax_id", length = 10)
    private String taxId;

    @Column(name = "tax_name", columnDefinition = "NVARCHAR(50)")
    private String taxName;

    @Column(name = "tax_rate", precision = 5, scale = 2)
    private BigDecimal taxRate;

    @Column(name = "is_delete", columnDefinition = "BIT DEFAULT 0", nullable = false)
     private Boolean isDelete;

    @OneToMany(mappedBy = "tax")
    private List<Product> products;

}
