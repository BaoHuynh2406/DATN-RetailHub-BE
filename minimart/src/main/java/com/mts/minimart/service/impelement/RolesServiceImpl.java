package com.mts.minimart.service.impelement;

import com.mts.minimart.data.dto.RolesDto;
import com.mts.minimart.data.dto.UsersDto;
import com.mts.minimart.data.entity.Roles;
import com.mts.minimart.data.entity.Users;
import com.mts.minimart.data.repository.RolesRepository;
import com.mts.minimart.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolesServiceImpl implements RolesService
{

    @Autowired
    RolesRepository rolesRepository;

    @Override
    public void saveRole(Roles role)
    {
        rolesRepository.save(role);
    }

    @Override
    public void deleteRole(Roles role)
    {

    }

    @Override
    public List<RolesDto> getRoles()
    {
        return RolesDto.convertListRoleEtoListRoleDto(rolesRepository.findAll());
    }


}
