package com.mts.minimart.data.dto;

import com.mts.minimart.data.entity.Roles;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class RolesDto
{
    private String idRole;

    private String roleName;

    public static RolesDto convertRoleEtoRoleDto(Roles roles)
    {
        return RolesDto.builder()
                .idRole(roles.getIdRole())
                .roleName(roles.getRoleName())
                .build();
    }

    public static List<RolesDto> convertListRoleEtoListRoleDto(List<Roles> roles)
    {
        return roles.stream().map(Roles -> convertRoleEtoRoleDto(Roles)).toList();
    }

}
