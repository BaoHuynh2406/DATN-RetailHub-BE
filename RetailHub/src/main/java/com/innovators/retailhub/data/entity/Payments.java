package com.innovators.retailhub.data.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@Table(name = "payments")
public class Payments
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Integer paymentId;
    @Column(name = "invoice_id")
    private int invoiceId;
    @Column(name = "payment_method_id")
    private int paymentMethodId;
    @Column(name = "amount", nullable = false)
    private Double amount;
    @Column(name = "payment_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate;

    // Khóa ngoại
    @ManyToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethods paymentMethods;
}

