package com.project.retailhub.service;


import com.project.retailhub.data.dto.request.EmployeeRequest;
import com.project.retailhub.data.dto.response.employees.EmployeeResponseFull;

import java.util.List;


public interface EmployeesService
{
    void addNewEmployee(EmployeeRequest request);

    void updateEmployee(EmployeeRequest request);

    EmployeeResponseFull getEmployee(long idEmployee);

    void deleteEmployee(long idEmployee);

    List<EmployeeResponseFull> findAllEmployees();

    EmployeeResponseFull getByEmail(String email);
}
