package com.project.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Nationalized;

import java.time.OffsetDateTime;
import java.util.Date;

@Entity
@Table(name ="point_history")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id", columnDefinition = "BIGINT")
    private Long historyId;

    @Column(name = "transaction_date")
    private OffsetDateTime transactionDate;


    @Column(name = "points", columnDefinition = "INT", nullable = false)
    private int points;

    @Nationalized
    @Column(name = "description", columnDefinition = "NVARCHAR(100)", nullable = false)
    private String description;

    // Khóa ngoại
    private long userId;

    private long customerId;

    private long invoiceId;
}
