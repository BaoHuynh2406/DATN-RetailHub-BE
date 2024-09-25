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

    User toUser(UserRequest request, @Context RoleRepository roleRepository);

    // Chuyển đổi từ User entity sang UserResponse
    @Mapping(source = "roleId", target = "role", qualifiedByName = "roleToRoleRespone")
    UserResponse toUserResponse(User user, @Context RoleRepository roleRepository);


    // Chuyển đổi từ Roles entity sang RoleRespone DTO
    @Named("roleToRoleRespone")
    default RoleRespone mapRoleToRoleRespone(String roleId, @Context RoleRepository roleRepository) {
        Role r = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        return RoleRespone.builder()
                .roleId(r.getRoleId())
                .roleDescription(r.getRoleDescription())
                .build();
    }


    // Phương thức chuyển đổi danh sách Employees thành danh sách EmployeeResponse
    List<UserResponse> toUserResponseList(List<User> users, @Context RoleRepository roleRepository);

    // Phương thức chuyển đổi danh sách EmployeeRequest thành danh sách Employees
    List<User> toEmployeesList(List<UserRequest> requests);

}
