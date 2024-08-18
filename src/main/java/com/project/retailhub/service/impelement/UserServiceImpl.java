package com.project.retailhub.service.impelement;


import com.project.retailhub.data.dto.request.UserRequest;
import com.project.retailhub.data.dto.response.UserResponse;
import com.project.retailhub.data.entity.User;
import com.project.retailhub.data.mapper.UserMapper;
import com.project.retailhub.data.repository.UserRepository;
import com.project.retailhub.data.repository.RoleRepository;
import com.project.retailhub.exception.AppException;
import com.project.retailhub.exception.ErrorCode;
import com.project.retailhub.service.UserService;
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
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;



    @Override
    public void addNewEmployee(UserRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        if (userRepository.existsByEmail(request.getEmail()))
            throw new AppException(ErrorCode.USER_EXISTED);

        //Thực hiện chuyển đồi request thành entity
        User user = userMapper.toUser(request, roleRepository);
//        Mã hóa mật khẩu
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
    }

    @Override
    public void updateEmployee(UserRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        // Kiểm tra xem ID có phải là null không
        long id = request.getEmployeeId();
        if (id == 0) {
            throw new AppException(ErrorCode.EMPLOYEE_ID_NULL);
        }

        // Tìm kiếm nhân viên theo ID
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // Cập nhật thông tin nhân viên
        //Mã hóa mật khẩu ở đây
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setAddress(request.getAddress());

        // Cập nhật vai trò
        user.setRole(roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND)));

        user.setImageName(request.getImage());
        user.setStartDate(request.getStartDate());
        user.setEndDate(request.getEndDate());
        user.setIsActive(request.getStatus());

        // Lưu nhân viên đã cập nhật
        userRepository.save(user);
    }

    @Override
    public UserResponse getEmployee(long idEmployee) {
        return userMapper.toUserResponse(
                userRepository.findById(idEmployee)
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    @Override
    public UserResponse getMyInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }

        String email = authentication.getName(); // Lấy user name người dùng ở đây là email
        return getByEmail(email);
    }

    @Override
    public void deleteEmployee(long idEmployee) {
        userRepository.deleteById(idEmployee);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')") //Yêu cầu quyền admin
    public List<UserResponse> findAllEmployees() {
        return userMapper.toUserResponseList(userRepository.findAll());
    }


    @Override
    public UserResponse getByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }
}
