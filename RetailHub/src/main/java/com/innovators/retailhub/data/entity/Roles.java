package com.innovators.retailhub.data.entity;


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
    private String roleId;

    private String roleDescription;
}
