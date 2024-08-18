package com.project.retailhub.service;


import com.project.retailhub.data.dto.request.UserRequest;
import com.project.retailhub.data.dto.response.UserResponse;

import java.util.List;


public interface UserService
{
    void addNewEmployee(UserRequest request);

    void updateEmployee(UserRequest request);

    UserResponse getEmployee(long idEmployee);

    UserResponse getMyInfo();

    void deleteEmployee(long idEmployee);

    List<UserResponse> findAllEmployees();

    UserResponse getByEmail(String email);
}
