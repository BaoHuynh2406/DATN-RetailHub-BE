package com.project.retailhub.data.mapper;

import com.project.retailhub.data.dto.request.UserRequest;
import com.project.retailhub.data.dto.response.UserResponse;
import com.project.retailhub.data.dto.response.RoleRespone;
import com.project.retailhub.data.entity.User;
import com.project.retailhub.data.entity.Role;
import com.project.retailhub.data.repository.RoleRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // Chuyển đổi từ UserRequest sang User
    @Mapping(source = "roleId", target = "role", qualifiedByName = "roleIdToRole")
    User toUser(UserRequest request, @Context RoleRepository roleRepository);

    // Chuyển đổi từ roleId thành Role entity
    @Named("roleIdToRole")
    default Role mapRoleIdToRole(String roleId, @Context RoleRepository roleRepository) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }

    // Chuyển đổi từ User entity sang UserResponse
    @Mapping(source = "role", target = "role", qualifiedByName = "roleToRoleRespone")
    UserResponse toUserResponse(User user);


    // Chuyển đổi từ Roles entity sang RoleRespone DTO
    @Named("roleToRoleRespone")
    default RoleRespone mapRoleToRoleRespone(Role role) {
        if (role == null) {
            return null;
        }

        return RoleRespone.builder()
                .roleId(role.getRoleId())
                .roleDescription(role.getRoleDescription())
                .build();
    }


    // Phương thức chuyển đổi danh sách Employees thành danh sách EmployeeResponse
    List<UserResponse> toUserResponseList(List<User> users);

    // Phương thức chuyển đổi danh sách EmployeeRequest thành danh sách Employees
    List<User> toEmployeesList(List<UserRequest> requests);

}
