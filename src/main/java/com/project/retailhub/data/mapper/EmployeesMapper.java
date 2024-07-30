package com.project.retailhub.data.mapper;


import com.project.retailhub.data.dto.request.EmployeeRequest;
import com.project.retailhub.data.dto.response.EmployeeResponse;
import com.project.retailhub.data.entity.Employees;
import com.project.retailhub.data.entity.Roles;
import com.project.retailhub.data.repository.RolesRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeesMapper {

    Employees toEmployees(EmployeeRequest request);

    @Named("roleIdToRole")
    default Roles mapRoleIdToRole(String roleId, @Context RolesRepository roleRepository) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));
    }

    EmployeeResponse toEmployeeResponse(Employees employee);


    // Phương thức chuyển đổi danh sách Employees thành danh sách EmployeeResponseFull
    List<EmployeeResponse> toEmployeeResponseList(List<Employees> employees);

    // Phương thức chuyển đổi danh sách EmployeeRequest thành danh sách Employees
    List<Employees> toEmployeesList(List<EmployeeRequest> requests);

}
