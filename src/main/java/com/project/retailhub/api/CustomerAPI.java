package com.project.retailhub.api;

import com.project.retailhub.data.dto.request.CustomerRequest;
import com.project.retailhub.data.dto.request.UserRequest;
import com.project.retailhub.data.dto.response.CustomeResponse;
import com.project.retailhub.data.dto.response.ResponseObject;
import com.project.retailhub.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        log.info("Get ALL Users");
        return resultApi;
    }
        @PostMapping("/create")
    public ResponseObject<?> doPostCreateCustomer(@RequestBody CustomerRequest request) {
        var resultApi = new ResponseObject<>();
        customerService.addCustomer(request);
        resultApi.setMessage("Employee added successfully");
        log.info("Added employee successfully");
        return resultApi;
    }
    @PutMapping("/update")
    public ResponseObject<?> doPostUpdateCustomer(@RequestBody CustomerRequest request) {
        var resultApi = new ResponseObject<>();
        customerService.updateCustomer(request);
        resultApi.setMessage("Employee updated successfully");
        log.info("Updated employee with ID " + request.getCustomerId() + " successfully");
        return resultApi;
    }

    @GetMapping("/{customerId}")
    public ResponseObject<?> doGetCustomer(@PathVariable("customerId") long customerId) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(customerService.getCustomerById(customerId));
        log.info("Get employee with ID " + customerId);
        return resultApi;
    }

    @DeleteMapping("/Delete/{customerId}")
    public ResponseObject<?> doDeleteCustomer(@PathVariable("customerId") long customerId) {
        var resultApi = new ResponseObject<>();
        customerService.deleteCustomer(customerId);
        resultApi.setMessage("Khách hàng đã được xóa thành công");
        log.info("Xóa khách hàng với ID " + customerId);
        return resultApi;
    }

    @PostMapping("/restore/{customerId}")
    public ResponseObject<?> doRestoreCustomer(@PathVariable("customerId") long customerId) {
        var resultApi = new ResponseObject<>();
        customerService.restoreCustomer(customerId);
        resultApi.setMessage("Khách hàng đã được khôi phục thành công");
        log.info("Khôi phục khách hàng với ID " + customerId);
        return resultApi;
    }

    @GetMapping("/getByPhoneNumber/{phoneNumber}")
    public ResponseObject<?> doGetCustomerByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(customerService.getCustomerByPhoneNumber(phoneNumber));
        log.info("Lấy thông tin khách hàng với số điện thoại " + phoneNumber);
        return resultApi;
    }

    @GetMapping("/deleted")
    public ResponseObject<?> doGetAllDeletedCustomers() {
        var resultApi = new ResponseObject<>();
        resultApi.setData(customerService.getAllDeletedCustomers());
        log.info("Lấy tất cả khách hàng đã bị xóa");
        return resultApi;
    }
}

