package com.project.retailhub.service;


import com.project.retailhub.data.dto.request.EmployeeRequest;
import com.project.retailhub.data.dto.response.EmployeeResponse;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;


public interface EmployeesService
{
    void addNewEmployee(EmployeeRequest request);

    void updateEmployee(EmployeeRequest request);

    EmployeeResponse getEmployee(long idEmployee);

    EmployeeResponse getMyInfo();

    void deleteEmployee(long idEmployee);

    List<EmployeeResponse> findAllEmployees();

    EmployeeResponse getByEmail(String email);
}
