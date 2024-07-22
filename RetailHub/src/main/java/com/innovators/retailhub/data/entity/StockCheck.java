package com.innovators.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "stock_check")
public class StockCheck
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_check_id")
    private Integer stockCheckId;
    @Column(name = "stock_check_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date stockCheckDate;

    // Khóa ngoại
}

