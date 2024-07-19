package com.innovators.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name ="PointHistory")
@Data
public class PointHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int historyId;
    private int customerId;
    private Date transactionDate;
    private int points;
    private String transactionType;
    private String description;
}
