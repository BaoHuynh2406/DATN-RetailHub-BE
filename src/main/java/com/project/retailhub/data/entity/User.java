package com.project.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    private String fullName;

    private String password;

    private String email;

    private String phoneNumber;

    private String address;

    private String imageName;

    private Date startDate;

    private Date endDate;

    private Date birthday;

    private Boolean isActive;

    private Boolean isDelete;

    // Khóa ngoại
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<PointHistory> pointHistories;

    @OneToMany(mappedBy = "user")
    private List<Invoice> invoices;

    @OneToMany(mappedBy = "user")
    private List<StockCheck> stockChecks;
}
