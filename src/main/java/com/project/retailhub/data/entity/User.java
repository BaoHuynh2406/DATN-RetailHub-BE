package com.project.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Nationalized;

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

    @Nationalized
    @Column(name = "full_name", columnDefinition = "NVARCHAR(100)", nullable = false)
    private String fullName;

    @Column(name = "password", columnDefinition = "VARCHAR(500)", nullable = false)
    private String password;

    @Column(name = "email", columnDefinition = "VARCHAR(50)", nullable = false)
    private String email;

    @Column(name = "phone_number", columnDefinition = "VARCHAR(15)", nullable = false)
    private String phoneNumber;

    @Nationalized
    @Column(name = "address", columnDefinition = "NVARCHAR(200)")
    private String address;

    @Nationalized
    @Column(name = "image_name", columnDefinition = "NVARCHAR(200)")
    private String imageName;

    @Column(name = "start_date", columnDefinition = "DATE", nullable = false)
    private Date startDate;

    @Column(name = "end_date", columnDefinition = "DATE")
    private Date endDate;

    @Column(name = "birthday", columnDefinition = "DATE")
    private Date birthday;

    @Column(name = "is_active", columnDefinition = "BIT DEFAULT 1", nullable = false)
    private Boolean isActive;

    @Column(name = "is_delete", columnDefinition = "BIT DEFAULT 0", nullable = false)
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
