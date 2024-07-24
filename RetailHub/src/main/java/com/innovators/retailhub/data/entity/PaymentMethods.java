package com.innovators.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name ="PaymentMethods")
@Data
public class PaymentMethods
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_method_id")
    private int paymentMethodId;
    @Column(name = "payment_name", nullable = false, length = 50)
    private String paymentName;
    @Column(name = "image", length = 100)
    private String image;

    // Khóa ngoại
}
