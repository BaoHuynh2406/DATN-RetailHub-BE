package com.project.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Nationalized;

import java.util.List;

@Entity
@Table(name = "customers")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id", columnDefinition = "BIGINT")
    private Long customerId;

    @Nationalized
    @Column(name = "full_name", columnDefinition = "NVARCHAR(100)", nullable = false)
    private String fullName;

    @Column(name = "phone_number", columnDefinition = "VARCHAR(15)", nullable = false)
    private String phoneNumber;

    @Column(name = "points", columnDefinition = "INT", nullable = false)
    private int points;

    @Column(name = "is_active", columnDefinition = "BIT", nullable = false)
    private Boolean isActive;

    @Column(name = "is_delete", columnDefinition = "BIT", nullable = false)
    private Boolean isDelete;

}
