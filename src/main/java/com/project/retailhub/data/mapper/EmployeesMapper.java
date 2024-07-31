package com.project.retailhub.data.mapper;

import com.project.retailhub.data.dto.request.EmployeeRequest;
import com.project.retailhub.data.dto.response.EmployeeResponse;
import com.project.retailhub.data.dto.response.RoleRespone;
import com.project.retailhub.data.entity.Employees;
import com.project.retailhub.data.entity.Roles;
import com.project.retailhub.data.repository.RolesRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeesMapper {

    // Chuyển đổi từ EmployeeRequest sang Employees
    @Mapping(source = "roleId", target = "role", qualifiedByName = "roleIdToRole")
    Employees toEmployees(EmployeeRequest request, @Context RolesRepository roleRepository);

    // Chuyển đổi từ roleId thành Role entity
    @Named("roleIdToRole")
    default Roles mapRoleIdToRole(String roleId, @Context RolesRepository roleRepository) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }

    // Chuyển đổi từ Roles entity sang RoleRespone DTO
    @Named("roleToRoleRespone")
    default RoleRespone mapRoleToRoleRespone(Roles role) {
        if (role == null) {
            return null;
        }
        return RoleRespone.builder()
                .roleId(role.getRoleId())
                .roleDescription(role.getRoleDescription())
                .build();
    }

    // Chuyển đổi từ Employees entity sang EmployeeResponse DTO
    @Mapping(source = "role", target = "role", qualifiedByName = "roleToRoleRespone")
    EmployeeResponse toEmployeeResponse(Employees employee);

    // Phương thức chuyển đổi danh sách Employees thành danh sách EmployeeResponse
    List<EmployeeResponse> toEmployeeResponseList(List<Employees> employees);

    // Phương thức chuyển đổi danh sách EmployeeRequest thành danh sách Employees
    List<Employees> toEmployeesList(List<EmployeeRequest> requests);

}
