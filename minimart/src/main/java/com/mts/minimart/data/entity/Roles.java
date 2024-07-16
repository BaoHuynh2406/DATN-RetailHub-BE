package com.mts.minimart.data.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "roles")
public class Roles
{

    @Id
    @Column(name = "id_role")
    private String idRole;

    @Column(name = "role_name")
    private String roleName;

    @OneToMany(mappedBy = "role")
    private Set<Users> users = new HashSet<>();




}
