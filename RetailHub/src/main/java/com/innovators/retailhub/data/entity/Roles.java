package com.innovators.retailhub.data.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "roles")
public class Roles
{
    @Id
    @Column(name = "role_id")
    private String roleId;
    private String roleDescription;
}
