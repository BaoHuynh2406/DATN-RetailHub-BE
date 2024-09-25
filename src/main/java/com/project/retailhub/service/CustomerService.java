package com.project.retailhub.service;

import com.project.retailhub.data.dto.request.CustomerRequest;
import com.project.retailhub.data.dto.response.CustomeResponse;

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
}
