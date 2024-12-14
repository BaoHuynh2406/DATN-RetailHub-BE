package com.project.retailhub.api.CustomerAPI;

import com.project.retailhub.data.dto.request.CustomerRequest;
import com.project.retailhub.data.dto.response.ResponseObject;
import com.project.retailhub.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/customer")
public class CustomerAPI {

    final CustomerService customerService;

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CS')")
    @GetMapping("/getAll")
    public ResponseObject<?> doGetCustomer() {
        var resultApi = new ResponseObject<>();
        resultApi.setData(customerService.getAllActiveCustomers());
        log.info("Fetched all active customers");
        return resultApi;
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CS')")
    @PostMapping("/create")
    public ResponseObject<?> doPostCreateCustomer(@RequestBody CustomerRequest request) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(customerService.addCustomer(request));
        resultApi.setMessage("Customer added successfully");
        log.info("Added customer successfully");
        return resultApi;
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CS')")
    @PutMapping("/update")
    public ResponseObject<?> doPostUpdateCustomer(@RequestBody CustomerRequest request) {
        var resultApi = new ResponseObject<>();
        customerService.updateCustomer(request);
        resultApi.setMessage("Customer updated successfully");
        log.info("Updated customer with ID " + request.getCustomerId() + " successfully");
        return resultApi;
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CS')")
    @GetMapping("/{customerId}")
    public ResponseObject<?> doGetCustomer(@PathVariable("customerId") long customerId) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(customerService.getCustomerById(customerId));
        log.info("Fetched customer with ID " + customerId);
        return resultApi;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/Delete/{customerId}")
    public ResponseObject<?> doDeleteCustomer(@PathVariable("customerId") long customerId) {
        var resultApi = new ResponseObject<>();
        customerService.deleteCustomer(customerId);
        resultApi.setMessage("Customer deleted successfully");
        log.info("Deleted customer with ID " + customerId);
        return resultApi;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
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

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CS')")
    @GetMapping("/find")
    public ResponseObject<?> doFindCustomerByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(customerService.findCustomerByPhoneNumber(phoneNumber));
        log.info("Fetched customers with phone number " + phoneNumber);
        return resultApi;
    }
}
