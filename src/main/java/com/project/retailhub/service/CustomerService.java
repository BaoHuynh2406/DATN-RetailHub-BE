package com.project.retailhub.service;

import com.project.retailhub.data.dto.request.CustomerRequest;
import com.project.retailhub.data.dto.response.CustomeResponse;

import java.util.List;

public interface CustomerService {

    void addNewCustomer(CustomerRequest request);

    void updateCustomer(CustomerRequest request);

    CustomeResponse getCustomer(long customerId);

    void deleteCustomer(long customerId);

    void restoreCustomer(long customerId);

    List<CustomeResponse> getAllActiveCustomers();

    List<CustomeResponse> getAllDeletedCustomers();

    CustomeResponse getByPhoneNumber(String phoneNumber);
}
