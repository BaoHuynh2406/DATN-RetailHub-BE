package com.project.retailhub.api;

import com.project.retailhub.data.dto.request.CustomerRequest;
import com.project.retailhub.data.dto.response.ResponseObject;
import com.project.retailhub.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/customer")
public class CustomerAPI {

    final CustomerService customerService;

    // Get all active customers
    @GetMapping("/getAllActive")
    public ResponseObject<?> getAllActiveCustomers() {
        var resultApi = new ResponseObject<>();
        resultApi.setData(customerService.getAllActiveCustomers());
        log.info("Get all active customers");
        return resultApi;
    }

    // Get all deleted customers
    @GetMapping("/getAllDeleted")
    public ResponseObject<?> getAllDeletedCustomers() {
        var resultApi = new ResponseObject<>();
        resultApi.setData(customerService.getAllDeletedCustomers());
        log.info("Get all deleted customers");
        return resultApi;
    }

    // Get customer by ID
    @GetMapping("/{customerId}")
    public ResponseObject<?> getCustomerById(@PathVariable("customerId") long customerId) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(customerService.getCustomer(customerId));
        log.info("Get customer with ID " + customerId);
        return resultApi;
    }

    // Get customer by phone number
    @GetMapping("/phone/{phoneNumber}")
    public ResponseObject<?> getCustomerByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(customerService.getByPhoneNumber(phoneNumber));
        log.info("Get customer with phone number " + phoneNumber);
        return resultApi;
    }

    // Create new customer
    @PostMapping("/create")
    public ResponseObject<?> createCustomer(@RequestBody CustomerRequest request) {
        var resultApi = new ResponseObject<>();
        customerService.addNewCustomer(request);
        resultApi.setMessage("Customer added successfully");
        log.info("Added customer successfully");
        return resultApi;
    }

    // Update customer
    @PutMapping("/update")
    public ResponseObject<?> updateCustomer(@RequestBody CustomerRequest request) {
        var resultApi = new ResponseObject<>();
        customerService.updateCustomer(request);
        resultApi.setMessage("Customer updated successfully");
        log.info("Updated customer with ID " + request.getCustomerId() + " successfully");
        return resultApi;
    }

    // Delete customer
    @DeleteMapping("/delete/{customerId}")
    public ResponseObject<?> deleteCustomer(@PathVariable("customerId") long customerId) {
        var resultApi = new ResponseObject<>();
        customerService.deleteCustomer(customerId);
        resultApi.setMessage("Customer deleted successfully");
        log.info("Deleted customer with ID " + customerId + " successfully");
        return resultApi;
    }

    // Restore deleted customer
    @PutMapping("/restore/{customerId}")
    public ResponseObject<?> restoreCustomer(@PathVariable("customerId") long customerId) {
        var resultApi = new ResponseObject<>();
        customerService.restoreCustomer(customerId);
        resultApi.setMessage("Customer restored successfully");
        log.info("Restored customer with ID " + customerId + " successfully");
        return resultApi;
    }
}
