package com.innovators.retailhub.data.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@Table(name = "payments")
public class Payments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Integer paymentId;

    @Column(name = "invoice_id", nullable = false)
    private Integer invoiceId;

    @Column(name = "payment_method_id", nullable = false)
    private Integer paymentMethodId;

    @Column(name = "amount", nullable = false, precision = 18, scale = 2)
    private Double amount;

    @Column(name = "payment_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate;

    // Nếu có quan hệ với các entity khác thì cần thêm @ManyToOne, @OneToMany, @JoinColumn, v.v...
}

