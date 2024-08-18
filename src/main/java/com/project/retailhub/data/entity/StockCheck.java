package com.project.retailhub.data.entity;

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
    private Long stockCheckId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date stockCheckDate;

    // Khóa ngoại
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "stockCheck")
    private List<StockCheckItem> stockCheckItems;
}

