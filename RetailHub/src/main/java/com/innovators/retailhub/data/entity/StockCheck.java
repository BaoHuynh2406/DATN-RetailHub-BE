package com.innovators.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

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
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employees employee;

    @OneToMany(mappedBy = "stockCheck")
    private List<StockCheckItem> stockCheckItems;
}

