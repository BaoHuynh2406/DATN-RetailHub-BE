package com.project.retailhub.service;

import com.project.retailhub.data.dto.EmployeeDTO;
import com.project.retailhub.data.entity.Employees;
import java.util.List;

public interface EmployeesService
{
    void saveEmployee(Employees employees);

    void deleteEmployee(Employees employees);

    List<EmployeeDTO> findAllEmployees();

    List<Employees> findAll();

    EmployeeDTO getByEmail(EmployeeDTO employee);
}
