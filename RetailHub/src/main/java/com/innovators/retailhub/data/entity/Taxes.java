package com.innovators.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "taxes")
public class Taxes
{
    @Id
    private String taxId;

    private String taxName;

    private BigDecimal taxRate;
}
