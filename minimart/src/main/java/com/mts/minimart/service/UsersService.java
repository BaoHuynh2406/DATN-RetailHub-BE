package com.mts.minimart.service;

import com.mts.minimart.data.dto.UsersDto;
import com.mts.minimart.data.entity.Users;

import java.util.List;

public interface UsersService
{
    void saveUser(Users user);

    void deleteUser(Users user);

    List<UsersDto> findAllUsers();

    public List<UsersDto> getUsersByRoleId(String roleId);
}
