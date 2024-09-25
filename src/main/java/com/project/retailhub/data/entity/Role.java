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
public class Role {

    @Id
    @Column(name = "role_id", length = 10, columnDefinition = "VARCHAR(10)")
    private String roleId;

    @Column(name = "role_description", columnDefinition = "NVARCHAR(100)", nullable = false)
    private String roleDescription;

}
