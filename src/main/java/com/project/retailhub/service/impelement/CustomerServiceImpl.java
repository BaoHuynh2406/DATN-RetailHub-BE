package com.project.retailhub.service.impelement;

import com.project.retailhub.data.dto.request.CustomerRequest;
import com.project.retailhub.data.dto.response.CustomeResponse;
import com.project.retailhub.data.entity.Customer;
import com.project.retailhub.data.mapper.CustomerMapper;
import com.project.retailhub.data.repository.CustomerRepository;
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
        Customer customer = customerMapper.toCustomer(request);
        customerRepository.save(customer);
    }

    @Override
    public void updateCustomer(CustomerRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customerMapper.toCustomer(request); // Cập nhật thông tin
        customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customer.setIsDelete(true);
        customerRepository.save(customer);
    }

    @Override
    public void restoreCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customer.setIsDelete(false);
        customerRepository.save(customer);
    }

    @Override
    public CustomeResponse getCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .map(customerMapper::toCustomerResponse)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    @Override
    public CustomeResponse getCustomerByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneNumber(phoneNumber)
                .map(customerMapper::toCustomerResponse)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
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
