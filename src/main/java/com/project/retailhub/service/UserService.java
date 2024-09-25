package com.project.retailhub.service;


import com.project.retailhub.data.dto.request.UserRequest;
import com.project.retailhub.data.dto.response.Pagination.PageResponse;
import com.project.retailhub.data.dto.response.UserResponse;

import java.util.List;


public interface UserService
{
    void addNewUser(UserRequest request);

    void updateUser(UserRequest request);

    UserResponse getUser(long idEmployee);

    UserResponse getMyInfo();

    void deleteUser(long idEmployee);

    void restoreUser(long idEmployee);

    void toggleActiveUser(long idEmployee);

    List<UserResponse> findAllUser();

    PageResponse<UserResponse> findAllLimit(int page, int size);
    List<UserResponse> findAllAvailableUsers();

    List<UserResponse> findAllDeletedUsers();

    UserResponse getByEmail(String email);


}
