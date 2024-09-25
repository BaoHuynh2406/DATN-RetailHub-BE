package com.project.retailhub.service.impelement;

import com.project.retailhub.data.dto.request.UserRequest;
import com.project.retailhub.data.dto.response.Pagination.PageResponse;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;


    @Override
    public void addNewUser(UserRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        if (userRepository.existsByEmail(request.getEmail()))
            throw new AppException(ErrorCode.USER_ALREADY_EXISTS);

        //Thực hiện chuyển đồi request thành entity
        User user = userMapper.toUser(request, roleRepository);
        // Mã hóa mật khẩu
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
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
        if(request.getPassword() != null) user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setAddress(request.getAddress());

        // Cập nhật vai trò
        user.setRole(roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND)));

        user.setImageName(request.getImage());
        user.setStartDate(request.getStartDate());
        user.setEndDate(request.getEndDate());
        user.setIsActive(request.getIsActive());

        // Lưu nhân viên đã cập nhật
        userRepository.save(user);
    }

    @Override
    public UserResponse getUser(long userId) {
        return userMapper.toUserResponse(
                userRepository.findById(userId)
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
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
    @PreAuthorize("hasRole('ADMIN')") //Yêu cầu quyền admin
    public void deleteUser(long idEmployee) {
        User user = userRepository.findById(idEmployee)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        user.setIsDelete(true);
        userRepository.save(user);
    }

    @Override
    public List<UserResponse> findAllUser() {
        return userMapper.toUserResponseList(userRepository.findAll());
    }

    @Override
    public PageResponse<UserResponse> findAllLimit(int page, int size) {

        Sort sort = Sort.by("startDate").descending();
        Pageable pageable = PageRequest.of(page -1, size, sort);

        var pageData = userRepository.findAllLimit(userId, pageable);
        return null;
    }


    @Override
    public UserResponse getByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toUserResponse(user);
    }
}
