package com.innovators.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Data
@Table(name = "receiving")
public class Receving
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int recevingId;
    private int supplierId;
    private Date recevingDate;

}
