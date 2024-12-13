package com.project.retailhub.api;

import com.project.retailhub.data.dto.response.ResponseObject;
import com.project.retailhub.service.ThongKeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/thong-ke")
public class ThongKeAPI {
    private final ThongKeService sv;

    @GetMapping("/invoice-data-start-to-end")
    public ResponseObject<?> getChartData(
            @RequestParam(value = "startDate", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(value = "endDate", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @RequestParam(value = "status", required = false) String status
    ) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(sv.getInvoiceChartDataStartToEnd(startDate, endDate, status));
        log.info("Get invoiceData [" + startDate + "|" + endDate + "]");
        return resultApi;
    }
    @GetMapping("/active-customer-count")
    public ResponseObject<?> getActiveCustomerCount() {
        var resultApi = new ResponseObject<>();
        resultApi.setData(sv.getActiveCustomerCount());
        log.info("Get active customer count");
        return resultApi;
    }



}
