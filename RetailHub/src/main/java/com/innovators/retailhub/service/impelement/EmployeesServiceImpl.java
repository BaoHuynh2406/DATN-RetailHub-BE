package com.innovators.retailhub.service.impelement;

import com.innovators.retailhub.data.dto.EmployeesDTO;
import com.innovators.retailhub.data.entity.Employees;
import com.innovators.retailhub.data.repository.EmployeesRepository;
import com.innovators.retailhub.service.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeesServiceImpl implements EmployeesService
{

    @Autowired
    EmployeesRepository employeesRepository;

    @Override
    public void saveEmployee(Employees employees) {
        employeesRepository.save(employees);
    }

    @Override
    public void deleteEmployee(Employees employees) {
        employeesRepository.delete(employees);
    }

    @Override
    public List<EmployeesDTO> findAllEmployees() {
        return EmployeesDTO.convertToDTO(employeesRepository.findAll());
    }

    @Override
    public List<Employees> findAll() {
        return employeesRepository.findAll();
    }
}
