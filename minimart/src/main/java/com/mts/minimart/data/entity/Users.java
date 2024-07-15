package com.mts.minimart.data.entity;

import com.mts.minimart.data.dto.UsersDto;
import jakarta.persistence.*;

import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class Users
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private int idUser;

    @Column(name = "user_name")
    private String userName;

    @ManyToOne
    @JoinColumn(name = "id_role")
    private Roles role;


}
