package com.innovators.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Table(name = "employees")
@Data
public class Employees
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employeeId;
    private String fullName;
    private String password;
    private String email;
    private String phoneNumber;
    private String address;
    private String image;
    private Date startDate;
    private Date endDate;
    private Boolean status;

//    Khóa ngọoại
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Roles role;
}
