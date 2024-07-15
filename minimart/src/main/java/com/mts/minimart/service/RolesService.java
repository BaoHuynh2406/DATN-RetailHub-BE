package com.mts.minimart.service;

import com.mts.minimart.data.dto.RolesDto;
import com.mts.minimart.data.dto.UsersDto;
import com.mts.minimart.data.entity.Roles;
import com.mts.minimart.data.entity.Users;

import java.util.List;

public interface RolesService
{
    void saveRole (Roles role);

    void deleteRole (Roles role);

    List<RolesDto> getRoles();

}
