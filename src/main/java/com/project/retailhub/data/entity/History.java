package com.project.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDateTime;

@Entity
@Table(name = "history")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id", columnDefinition = "BIGINT")
    private Long historyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Nationalized
    @Column(name = "action", columnDefinition = "NVARCHAR(50)", nullable = false)
    private String action; // "tích điểm" hoặc "đổi điểm"

    @Column(name = "points", columnDefinition = "INT", nullable = false)
    private int points;

    @Column(name = "timestamp", columnDefinition = "DATETIME", nullable = false)
    private LocalDateTime timestamp;
}
