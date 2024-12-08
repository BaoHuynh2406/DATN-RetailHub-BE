package com.project.retailhub.service;

import com.project.retailhub.data.dto.request.CustomerRequest;
import com.project.retailhub.data.dto.response.CustomeResponse;
import com.project.retailhub.data.dto.response.Pagination.PageResponse;
import com.project.retailhub.data.dto.response.UserResponse;

import java.util.List;

public interface CustomerService {
    void addCustomer(CustomerRequest request);

    void updateCustomer(CustomerRequest request);

    void deleteCustomer(Long customerId);

    void restoreCustomer(Long customerId);

    CustomeResponse getCustomerById(Long customerId);

    CustomeResponse getCustomerByPhoneNumber(String phoneNumber);

    List<CustomeResponse> getAllActiveCustomers();

    List<CustomeResponse> getAllDeletedCustomers();

    //Tìm khách hàng theo số điện thoại
    List<CustomeResponse> findCustomerByPhoneNumber(String phone);

    //Phân Trang
    PageResponse<CustomeResponse> getAllCustomerPagination(int page, int size);
    PageResponse<CustomeResponse> getAllActiveCustomerPagination(int page, int size);
    PageResponse<CustomeResponse> getAllDeletedCustomerPagination(int page, int size);

}
