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

    @Column(name = "payment_name", columnDefinition = "NVARCHAR(50)")
    private String paymentName;

    @Column(name = "image", columnDefinition = "NVARCHAR(100)")
    private String image;

    // Khóa ngoại
    @OneToMany(mappedBy = "paymentMethod")
    private List<Payment> payments;
}
