package com.project.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "receiving")
public class Receiving {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long receivingId;

    @Column(name = "receiving_date", nullable = false)
    private Date receivingDate;

    private Long supplierId;

    private long userId;


}
