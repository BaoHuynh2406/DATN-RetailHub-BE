package com.project.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Entity
@Data
@Table(name = "receiving")
public class Receiving
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long receivingId;
    private Date receivingDate;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "receiving")
    private List<ReceivingDetail> receivingDetails;
}
