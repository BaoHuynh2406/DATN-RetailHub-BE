package com.innovators.retailhub.data.entity;

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
    private int receivingId;
    private Date receivingDate;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Suppliers supplier;

    @OneToMany(mappedBy = "receiving")
    private List<ReceivingDetails> receivingDetails;
}
