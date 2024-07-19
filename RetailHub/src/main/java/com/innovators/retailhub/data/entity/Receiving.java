package com.innovators.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Data
@Table(name = "receiving")
public class Receiving
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int receivingId;
    private int supplierId;
    private Date recevingDate;

}
