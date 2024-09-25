package com.project.retailhub.api;

import com.project.retailhub.data.dto.response.ResponseObject;
import com.project.retailhub.service.CustomerService;
import com.project.retailhub.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v2/customer")
public class CustomerV2 {
    final CustomerService customerService;
    @GetMapping("/getAll")
    public ResponseObject<?> doGetCustomer(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(customerService.getAllCustomerPagination(page,size));
        log.info("Fetched all active customers");
        return resultApi;
    }

    @GetMapping("/deleted")
    public ResponseObject<?> doGetAllDeletedCustomers(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(customerService.getAllDeletedCustomerPagination(page,size));
        log.info("Fetched all deleted customers");
        return resultApi;
    }
}
