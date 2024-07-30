package com.project.retailhub.service.impelement;


import com.project.retailhub.data.dto.request.EmployeeRequest;
import com.project.retailhub.data.dto.response.EmployeeResponse;
import com.project.retailhub.data.entity.Employees;
import com.project.retailhub.data.mapper.EmployeesMapper;
import com.project.retailhub.data.repository.EmployeesRepository;
import com.project.retailhub.data.repository.RolesRepository;
import com.project.retailhub.service.EmployeesService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeesServiceImpl implements EmployeesService {

    EmployeesRepository employeesRepository;
    RolesRepository roleRepository;
    EmployeesMapper employeesMapper;


    @Override
    public void addNewEmployee(EmployeeRequest request) {
        if (employeesRepository.existsByEmail(request.getEmail()))
            throw new RuntimeException("Email already exists");

        Employees employee = employeesMapper.toEmployees(request);
        employeesRepository.save(employee);
    }

    @Override
    public void updateEmployee(EmployeeRequest request) {
        // Kiểm tra xem ID có phải là null không
        long id = request.getEmployeeId();
        if (id == 0) {
            throw new RuntimeException("Employee ID cannot be null");
        }

        // Tìm kiếm nhân viên theo ID
        Employees employee = employeesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Cập nhật thông tin nhân viên
        employee.setPassword(request.getPassword()); // Bạn có thể muốn mã hóa mật khẩu ở nơi khác
        employee.setFullName(request.getFullName());
        employee.setPhoneNumber(request.getPhoneNumber());
        employee.setAddress(request.getAddress());

        // Cập nhật vai trò
        employee.setRole(roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found")));

        employee.setImage(request.getImage());
        employee.setStartDate(request.getStartDate());
        employee.setEndDate(request.getEndDate());
        employee.setStatus(request.getStatus());

        // Lưu nhân viên đã cập nhật
        employeesRepository.save(employee);
    }

    @Override
    public EmployeeResponse getEmployee(long idEmployee) {
        return employeesMapper.toEmployeeResponse(
                employeesRepository.findById(idEmployee)
                        .orElseThrow(() -> new RuntimeException("Employee not found")));
    }

    @Override
    public void deleteEmployee(long idEmployee) {
        employeesRepository.deleteById(idEmployee);
    }

    @Override
    public List<EmployeeResponse> findAllEmployees() {
        return employeesMapper.toEmployeeResponseList(employeesRepository.findAll());
    }


    @Override
    public EmployeeResponse getByEmail(String email) {
        Employees employee = employeesRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Employee with email " + email + " not found"));

        return employeesMapper.toEmployeeResponse(employee);
    }
}
