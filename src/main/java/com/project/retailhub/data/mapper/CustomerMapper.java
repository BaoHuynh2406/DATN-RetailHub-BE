package com.project.retailhub.data.mapper;

import com.project.retailhub.data.dto.request.CustomerRequest;
import com.project.retailhub.data.dto.response.CustomeResponse;
import com.project.retailhub.data.entity.Customer;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    // Chuyển đổi từ CustomerRequest sang Customer entity
    Customer toCustomer(CustomerRequest request);

    // Chuyển đổi từ Customer entity sang CustomeResponse
    CustomeResponse toCustomerResponse(Customer customer);

    // Phương thức chuyển đổi danh sách Customer entities thành danh sách CustomeResponse
   public List<CustomeResponse> toCustomerResponseList(List<Customer> customers);

    // Phương thức chuyển đổi danh sách CustomerRequest thành danh sách Customer entities
    List<Customer> toCustomerList(List<CustomerRequest> requests);
}
