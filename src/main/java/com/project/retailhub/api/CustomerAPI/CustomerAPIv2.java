package com.project.retailhub.api.CustomerAPI;

import com.project.retailhub.data.dto.response.ResponseObject;
import com.project.retailhub.service.CustomerService;
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
public class CustomerAPIv2 {
    final CustomerService customerService;

    // API để lấy tất cả khách hàng đang hoạt động với phân trang
    @GetMapping("/getAll")
    public ResponseObject<?> doGetCustomer(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(customerService.getAllCustomerPagination(page, size));
        log.info("Fetched all active customers"); // Ghi log khi lấy danh sách khách hàng thành công
        return resultApi;
    }

    // API để lấy tất cả khách hàng đã bị xóa với phân trang
    @GetMapping("/deleted")
    public ResponseObject<?> doGetAllDeletedCustomers(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(customerService.getAllDeletedCustomerPagination(page, size));
        log.info("Fetched all deleted customers"); // Ghi log khi lấy danh sách khách hàng đã bị xóa thành công
        return resultApi;
    }
}
