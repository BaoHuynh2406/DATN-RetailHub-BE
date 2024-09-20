package com.project.retailhub.service.impelement;


import com.project.retailhub.data.dto.request.LogoutUserIdRequest;
import com.project.retailhub.data.dto.request.UserRequest;
import com.project.retailhub.data.dto.response.UserResponse;
import com.project.retailhub.data.entity.User;
import com.project.retailhub.data.mapper.UserMapper;
import com.project.retailhub.data.repository.UserRepository;
import com.project.retailhub.data.repository.RoleRepository;
import com.project.retailhub.exception.AppException;
import com.project.retailhub.exception.ErrorCode;
import com.project.retailhub.service.AuthenticationService;
import com.project.retailhub.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;
    AuthenticationService authenticationService;


    @Override
    public void addNewUser(UserRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        if (userRepository.existsByEmail(request.getEmail()))
            throw new AppException(ErrorCode.USER_ALREADY_EXISTS);

        //Thực hiện chuyển đồi request thành entity
        User user = userMapper.toUser(request, roleRepository);
        // Mã hóa mật khẩu
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new AppException(ErrorCode.USER_ALREADY_EXISTS);
        }
    }

    @Override
    public void updateUser(UserRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        // Kiểm tra xem ID có phải là null không
        long id = request.getUserId();
        if (id == 0) {
            throw new AppException(ErrorCode.USER_ID_NULL);
        }
        // Tìm kiếm nhân viên theo ID
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Cập nhật thông tin nhân viên
        //Mã hóa mật khẩu ở đây
        if (request.getPassword() != null) user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setAddress(request.getAddress());

        // Cập nhật vai trò
        user.setRoleId(request.getRoleId());

        user.setImageName(request.getImage());
        user.setStartDate(request.getStartDate());
        user.setEndDate(request.getEndDate());
        user.setIsActive(request.getIsActive());
        user.setEmail(request.getEmail());

        // Lưu nhân viên đã cập nhật
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new AppException(ErrorCode.USER_ALREADY_EXISTS);
        }
    }

    @Override
    public UserResponse getUser(long userId) {
        return userMapper.toUserResponse(
                userRepository.findById(userId)
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)), roleRepository);
    }

    @Override
    public UserResponse getMyInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        long userId = Long.parseLong(authentication.getName()); // Lấy user ID
        log.info("getInfoUser: userId=" + userId);
        return getUser(userId);
    }

    @Override
//    @PreAuthorize("ADMIN")
    public void deleteUser(long idEmployee) {
        User user = userRepository.findById(idEmployee)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        user.setIsDelete(true);
        user.setIsActive(false);
        //Thực hiện việc đăng xuất user đó ra luôn
        authenticationService.logout(LogoutUserIdRequest
                .builder()
                .userId(user.getUserId())
                .build());
        userRepository.save(user);
    }

    @Override
    public void restoreUser(long idEmployee) {
        User user = userRepository.findById(idEmployee)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        user.setIsDelete(false);
        userRepository.save(user);
    }

    @Override
    public void toggleActiveUser(long idEmployee) {
        User user = userRepository.findById(idEmployee)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        user.setIsActive(!user.getIsActive());
        //Nếu mà active bằng false thì đăng xuất hết.
        if (!user.getIsActive()) authenticationService.logout(LogoutUserIdRequest
                .builder()
                .userId(user.getUserId())
                .build());
        userRepository.save(user);
    }


    @Override
    public List<UserResponse> findAllUser() {
        return userMapper.toUserResponseList(userRepository.findAll(), roleRepository);
    }

    @Override
    public List<UserResponse> findAllAvailableUsers() {
        return userMapper.toUserResponseList(userRepository.findAllByIsDeleteFalse(), roleRepository);
    }

    @Override
    public List<UserResponse> findAllDeletedUsers() {
        return userMapper.toUserResponseList(userRepository.findAllByIsDeleteTrue(), roleRepository);
    }


    @Override
    public UserResponse getByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toUserResponse(user, roleRepository);
    }
}
