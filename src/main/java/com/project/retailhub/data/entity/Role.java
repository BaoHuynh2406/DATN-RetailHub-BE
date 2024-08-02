package com.project.retailhub.data.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Role
{
    @Id
    @Column(name = "role_id", length = 10)
    private String roleId;
    private String roleDescription;

    @OneToMany(mappedBy = "role")
    private List<Employee> employees;
}
