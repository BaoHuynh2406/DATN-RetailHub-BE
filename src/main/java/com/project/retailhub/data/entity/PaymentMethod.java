package com.project.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name ="PaymentMethods")
@Data
public class PaymentMethod
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentMethodId;
    private String paymentName;
    private String image;

    // Khóa ngoại
    @OneToMany(mappedBy = "paymentMethod")
    private List<Payment> payments;
}
