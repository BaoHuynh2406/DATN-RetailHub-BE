package com.project.retailhub.service.impelement;

import com.project.retailhub.data.dto.request.CustomerRequest;
import com.project.retailhub.data.dto.response.CustomeResponse;
import com.project.retailhub.data.entity.Customer;
import com.project.retailhub.data.repository.CustomerRepository;
import com.project.retailhub.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void addNewCustomer(CustomerRequest request) {
        Customer customer = Customer.builder()
                .fullName(request.getFullName())
                .phoneNumber(request.getPhoneNumber())
                .points(request.getPoints())
                .isActive(request.getIsActive())
                .isDelete(request.getIsDelete())
                .build();
        customerRepository.save(customer);
    }

    @Override
    public void updateCustomer(CustomerRequest request) {
        customerRepository.findById(request.getCustomerId()).ifPresent(customer -> {
            customer.setFullName(request.getFullName());
            customer.setPhoneNumber(request.getPhoneNumber());
            customer.setPoints(request.getPoints());
            customer.setIsActive(request.getIsActive());
            customer.setIsDelete(request.getIsDelete());
            customerRepository.save(customer);
        });
    }

    @Override
    public CustomeResponse getCustomer(long customerId) {
        return customerRepository.findById(customerId)
                .map(this::mapToResponse)
                .orElse(null);
    }

    @Override
    public void deleteCustomer(long customerId) {
        customerRepository.findById(customerId).ifPresent(customer -> {
            customer.setIsDelete(true);
            customer.setIsActive(false);
            customerRepository.save(customer);
        });
    }

    @Override
    public void restoreCustomer(long customerId) {
        customerRepository.findById(customerId).ifPresent(customer -> {
            customer.setIsDelete(false);
            customer.setIsActive(true);
            customerRepository.save(customer);
        });
    }

    @Override
    public List<CustomeResponse> getAllActiveCustomers() {
        return customerRepository.findByIsActiveTrue().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomeResponse> getAllDeletedCustomers() {
        return customerRepository.findByIsDeleteTrue().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CustomeResponse getByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneNumber(phoneNumber)
                .map(this::mapToResponse)
                .orElse(null);
    }

    private CustomeResponse mapToResponse(Customer customer) {
        return CustomeResponse.builder()
                .customerId(customer.getCustomerId())
                .fullName(customer.getFullName())
                .phoneNumber(customer.getPhoneNumber())
                .points(customer.getPoints())
                .isActive(customer.getIsActive())
                .isDelete(customer.getIsDelete())
                .build();
    }
}
