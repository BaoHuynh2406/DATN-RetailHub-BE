package com.project.retailhub.service.impelement;


import com.project.retailhub.data.dto.request.EmployeeRequest;
import com.project.retailhub.data.dto.response.EmployeeResponse;
import com.project.retailhub.data.entity.Employees;
import com.project.retailhub.data.mapper.EmployeesMapper;
import com.project.retailhub.data.repository.EmployeesRepository;
import com.project.retailhub.data.repository.RolesRepository;
import com.project.retailhub.exception.AppException;
import com.project.retailhub.exception.ErrorCode;
import com.project.retailhub.service.EmployeesService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeesServiceImpl implements EmployeesService {

    EmployeesRepository employeesRepository;
    RolesRepository roleRepository;
    EmployeesMapper employeesMapper;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);


    @Override
    public void addNewEmployee(EmployeeRequest request) {
        if (employeesRepository.existsByEmail(request.getEmail()))
            throw new AppException(ErrorCode.USER_EXISTED);

        //Thực hiện chuyển đồi request thành entity
        Employees employee = employeesMapper.toEmployees(request, roleRepository);
//        Mã hóa mật khẩu
        employee.setPassword(passwordEncoder.encode(request.getPassword()));

        employeesRepository.save(employee);
    }

    @Override
    public void updateEmployee(EmployeeRequest request) {
        // Kiểm tra xem ID có phải là null không
        long id = request.getEmployeeId();
        if (id == 0) {
            throw new AppException(ErrorCode.EMPLOYEE_ID_NULL);
        }

        // Tìm kiếm nhân viên theo ID
        Employees employee = employeesRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // Cập nhật thông tin nhân viên
        //Mã hóa mật khẩu ở đây
        employee.setPassword(passwordEncoder.encode(request.getPassword()));
        employee.setFullName(request.getFullName());
        employee.setPhoneNumber(request.getPhoneNumber());
        employee.setAddress(request.getAddress());

        // Cập nhật vai trò
        employee.setRole(roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND)));

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
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    @Override
    public EmployeeResponse getMyInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }

        String email = authentication.getName(); // Lấy user name người dùng ở đây là email
        return getByEmail(email);
    }

    @Override
    public void deleteEmployee(long idEmployee) {
        employeesRepository.deleteById(idEmployee);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')") //Yêu cầu quyền admin
    public List<EmployeeResponse> findAllEmployees() {
        return employeesMapper.toEmployeeResponseList(employeesRepository.findAll());
    }


    @Override
    public EmployeeResponse getByEmail(String email) {
        Employees employee = employeesRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return employeesMapper.toEmployeeResponse(employee);
    }
}
