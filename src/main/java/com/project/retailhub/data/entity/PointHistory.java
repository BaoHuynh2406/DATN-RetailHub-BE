package com.project.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name ="pointHistory")
@Data
public class PointHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private int historyId;
    @Column(name = "transaction_date", nullable = false)
    private Date transactionDate;
    @Column(name = "points", nullable = false)
    private int points;
    @Column(name = "transaction_type", nullable = false)
    private String transactionType;
    @Column(name = "description")
    private String description;

    // Khóa ngoại
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
