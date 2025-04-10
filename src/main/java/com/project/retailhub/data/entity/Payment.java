package com.project.retailhub.data.entity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payments")
public class Payment
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    private BigDecimal amount;

    private Date paymentDate;

    // Khóa ngoại
    private Long invoiceId;

    @Column(name = "payment_method_id", length = 10, columnDefinition = "VARCHAR(10)")
    private String paymentMethodId;

}
