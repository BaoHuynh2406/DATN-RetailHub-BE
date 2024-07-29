package com.project.retailhub.service.impelement;

import com.project.retailhub.data.dto.EmployeeDTO;
import com.project.retailhub.data.entity.Employees;
import com.project.retailhub.data.repository.EmployeesRepository;
import com.project.retailhub.service.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeesServiceImpl implements EmployeesService
{

    @Autowired
    EmployeesRepository employeesRepository;

    @Override
    public void saveEmployee(Employees employee) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        employeesRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Employees employees) {
        employeesRepository.delete(employees);
    }

    @Override
    public List<EmployeeDTO> findAllEmployees() {
        return EmployeeDTO.convertToDTO(employeesRepository.findAll());
    }

    @Override
    public List<Employees> findAll() {
        return employeesRepository.findAll();
    }

    @Override
    public EmployeeDTO getByEmail(EmployeeDTO employee) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        Employees employeeCheck = employeesRepository.getByEmail(employee.getEmail());
        if(employeeCheck == null)  return null;

        if(passwordEncoder.matches(employee.getPassword(), employeeCheck.getPassword())) {
            return EmployeeDTO.convertToDTO(employeeCheck);
        }
        return null;
    }
}
