package com.innovators.retailhub.data.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "roles")
public class Roles
{
    @Id
    @Column(name = "role_id", length = 10)
    private String roleId;
    private String roleDescription;

    // Khóa ngoại
    @OneToMany(mappedBy = "role")
    private List<Employees> employees;
}
