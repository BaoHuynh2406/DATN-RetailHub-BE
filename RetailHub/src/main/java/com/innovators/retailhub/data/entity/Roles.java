package com.innovators.retailhub.data.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "roles")
public class Roles
{
    @Id
    @Column(name = "role_id", length = 10)
    private String roleId;
    private String roleDescription;
}
