package com.innovators.retailhub.service;

import com.innovators.retailhub.data.dto.EmployeesDTO;
import com.innovators.retailhub.data.entity.Employees;
import java.util.List;

public interface EmployeesService
{
    void saveEmployee(Employees employees);

    void deleteEmployee(Employees employees);

    List<EmployeesDTO> findAllEmployees();

    List<Employees> findAll();
}
