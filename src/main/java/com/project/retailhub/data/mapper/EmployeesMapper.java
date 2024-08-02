package com.project.retailhub.data.mapper;

import com.project.retailhub.data.dto.request.EmployeeRequest;
import com.project.retailhub.data.dto.response.EmployeeResponse;
import com.project.retailhub.data.dto.response.RoleRespone;
import com.project.retailhub.data.entity.Employee;
import com.project.retailhub.data.entity.Role;
import com.project.retailhub.data.repository.RoleRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeesMapper {

    // Chuyển đổi từ EmployeeRequest sang Employees
    @Mapping(source = "roleId", target = "role", qualifiedByName = "roleIdToRole")
    Employee toEmployees(EmployeeRequest request, @Context RoleRepository roleRepository);

    // Chuyển đổi từ roleId thành Role entity
    @Named("roleIdToRole")
    default Role mapRoleIdToRole(String roleId, @Context RoleRepository roleRepository) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }

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

    // Chuyển đổi từ Employees entity sang EmployeeResponse DTO
    @Mapping(source = "role", target = "role", qualifiedByName = "roleToRoleRespone")
    EmployeeResponse toEmployeeResponse(Employee employee);

    // Phương thức chuyển đổi danh sách Employees thành danh sách EmployeeResponse
    List<EmployeeResponse> toEmployeeResponseList(List<Employee> employees);

    // Phương thức chuyển đổi danh sách EmployeeRequest thành danh sách Employees
    List<Employee> toEmployeesList(List<EmployeeRequest> requests);

}
