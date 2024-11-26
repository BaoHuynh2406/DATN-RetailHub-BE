package com.project.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name ="PaymentMethods")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethod
{
    @Id
    @Column(name = "payment_method_id", length = 10, columnDefinition = "VARCHAR(10)")
    private String paymentMethodId;

    @Column(name = "payment_name", columnDefinition = "NVARCHAR(50)")
    private String paymentName;

}
