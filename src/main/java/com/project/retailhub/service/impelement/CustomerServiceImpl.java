package com.project.retailhub.service.impelement;

import com.project.retailhub.data.dto.request.CustomerRequest;
import com.project.retailhub.data.dto.response.CustomeResponse;
import com.project.retailhub.data.entity.Customer;
import com.project.retailhub.data.mapper.CustomerMapper;
import com.project.retailhub.data.repository.CustomerRepository;
import com.project.retailhub.exception.AppException;
import com.project.retailhub.exception.ErrorCode;
import com.project.retailhub.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    final CustomerRepository customerRepository;
    final CustomerMapper customerMapper;

    @Override
    public void addCustomer(CustomerRequest request) {
        // Kiểm tra khách hàng đã tồn tại dựa trên số điện thoại
        if (customerRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new AppException(ErrorCode.CUSTOMER_ALREADY_EXISTS);
        }

        // Nếu không tồn tại, tiến hành thêm khách hàng mới
        Customer customer = customerMapper.toCustomer(request);
        customerRepository.save(customer);
    }


    @Override
    public void updateCustomer(CustomerRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOT_FOUND));

        // Cập nhật thông tin khách hàng
        customer.setFullName(request.getFullName());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setPoints(request.getPoints());
        customer.setIsActive(request.getIsActive());
        customer.setIsDelete(request.getIsDelete());

        // Lưu lại khách hàng đã được cập nhật
        customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOT_FOUND));

        if (customer.getIsDelete()) {
            throw new AppException(ErrorCode.CUSTOMER_ALREADY_DELETED);
        }

        customer.setIsDelete(true);
        customerRepository.save(customer);
    }

    @Override
    public void restoreCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOT_FOUND));

        if (!customer.getIsDelete()) {
            throw new AppException(ErrorCode.CUSTOMER_ALREADY_EXISTS); // Hoặc một lỗi khác phù hợp
        }

        customer.setIsDelete(false);
        customerRepository.save(customer);
    }

    @Override
    public CustomeResponse getCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .map(customerMapper::toCustomerResponse)
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOT_FOUND));
    }

    @Override
    public CustomeResponse getCustomerByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneNumber(phoneNumber)
                .map(customerMapper::toCustomerResponse)
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOT_FOUND));
    }

    @Override
    public List<CustomeResponse> getAllActiveCustomers() {
        return customerRepository.findAllByIsDeleteFalse()
                .stream().map(customerMapper::toCustomerResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomeResponse> getAllDeletedCustomers() {
        return customerRepository.findAllByIsDeleteTrue()
                .stream().map(customerMapper::toCustomerResponse)
                .collect(Collectors.toList());
    }
}
