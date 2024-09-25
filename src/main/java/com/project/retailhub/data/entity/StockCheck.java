package com.project.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stock_check")
public class StockCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockCheckId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "stock_check_date", nullable = false)
    private Date stockCheckDate;

    // Khóa ngoại
    private long userId;


}
