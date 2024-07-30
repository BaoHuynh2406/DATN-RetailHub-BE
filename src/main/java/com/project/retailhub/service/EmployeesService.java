package com.project.retailhub.service;


import com.project.retailhub.data.dto.request.EmployeeRequest;
import com.project.retailhub.data.dto.response.EmployeeResponse;

import java.util.List;


public interface EmployeesService
{
    void addNewEmployee(EmployeeRequest request);

    void updateEmployee(EmployeeRequest request);

    EmployeeResponse getEmployee(long idEmployee);

    void deleteEmployee(long idEmployee);

    List<EmployeeResponse> findAllEmployees();

    EmployeeResponse getByEmail(String email);
}
