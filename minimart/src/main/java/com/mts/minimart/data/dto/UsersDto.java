package com.mts.minimart.data.dto;


import com.mts.minimart.data.entity.Users;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UsersDto
{

    private int idUsers;

    private String userName;

    private String idRole;

    public static UsersDto convertUserEtoUserDto(Users user)
    {
        return UsersDto.builder()
                .idUsers(user.getIdUser())
                .userName(user.getUserName())
                .idRole(user.getRole().getIdRole())
                .build();
    }

    public static List<UsersDto> convertListUsersEtoListUsersDto(List<Users> users)
    {
        return users.stream()
               .map(User -> convertUserEtoUserDto(User))
               .collect(java.util.stream.Collectors.toList());
    }
}
