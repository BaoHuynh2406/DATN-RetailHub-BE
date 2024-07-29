package com.project.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "employees")
@Data
public class Employees {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private int employeeId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "image")
    private String image;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "status")
    private Boolean status;

    // Khóa ngoại
    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id", nullable = false)
    private Roles role; //Ok

    @OneToMany(mappedBy = "employee")
    private List<PointHistory> pointHistories;

    @OneToMany(mappedBy = "employee")
    private List<Invoices> invoices;

    @OneToMany(mappedBy = "employee")
    private List<StockCheck> stockChecks;
}
