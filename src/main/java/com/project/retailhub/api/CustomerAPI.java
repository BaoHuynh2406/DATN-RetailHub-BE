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

    @GetMapping("/getAll")
    public ResponseObject<?> doGetCustomer() {
        var resultApi = new ResponseObject<>();
        resultApi.setData(customerService.getAllActiveCustomers());
        log.info("Fetched all active customers");
        return resultApi;
    }

    @PostMapping("/create")
    public ResponseObject<?> doPostCreateCustomer(@RequestBody CustomerRequest request) {
        var resultApi = new ResponseObject<>();
        customerService.addCustomer(request);
        resultApi.setMessage("Customer added successfully");
        log.info("Added customer successfully");
        return resultApi;
    }

    @PutMapping("/update")
    public ResponseObject<?> doPostUpdateCustomer(@RequestBody CustomerRequest request) {
        var resultApi = new ResponseObject<>();
        customerService.updateCustomer(request);
        resultApi.setMessage("Customer updated successfully");
        log.info("Updated customer with ID " + request.getCustomerId() + " successfully");
        return resultApi;
    }

    @GetMapping("/{customerId}")
    public ResponseObject<?> doGetCustomer(@PathVariable("customerId") long customerId) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(customerService.getCustomerById(customerId));
        log.info("Fetched customer with ID " + customerId);
        return resultApi;
    }

    @DeleteMapping("/Delete/{customerId}")
    public ResponseObject<?> doDeleteCustomer(@PathVariable("customerId") long customerId) {
        var resultApi = new ResponseObject<>();
        customerService.deleteCustomer(customerId);
        resultApi.setMessage("Customer deleted successfully");
        log.info("Deleted customer with ID " + customerId);
        return resultApi;
    }

    @PostMapping("/restore/{customerId}")
    public ResponseObject<?> doRestoreCustomer(@PathVariable("customerId") long customerId) {
        var resultApi = new ResponseObject<>();
        customerService.restoreCustomer(customerId);
        resultApi.setMessage("Customer restored successfully");
        log.info("Restored customer with ID " + customerId);
        return resultApi;
    }

    @GetMapping("/getByPhoneNumber/{phoneNumber}")
    public ResponseObject<?> doGetCustomerByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(customerService.getCustomerByPhoneNumber(phoneNumber));
        log.info("Fetched customer with phone number " + phoneNumber);
        return resultApi;
    }

        @GetMapping("/deleted")
    public ResponseObject<?> doGetAllDeletedCustomers() {
        var resultApi = new ResponseObject<>();
        resultApi.setData(customerService.getAllDeletedCustomers());
        log.info("Fetched all deleted customers");
        return resultApi;
    }
}
