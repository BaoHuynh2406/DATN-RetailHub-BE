package com.innovators.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name ="PaymentMethods")
@Data
public class PaymentMethods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentMethodId;
    private String paymentName;
    private String image;
}
