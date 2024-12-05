package com.project.retailhub.api.InvoiceAPI;

import com.project.retailhub.data.dto.request.InvoiceRequest.InvoiceItemRequest;
import com.project.retailhub.data.dto.request.InvoiceRequest.InvoiceRequestCreate;
import com.project.retailhub.data.dto.response.ResponseObject;
import com.project.retailhub.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v2/invoice")
public class invoiceAPIv2 {
    final InvoiceService invoiceService;

    @GetMapping("/list")
    public ResponseObject<?> getInvoiceList(
            @RequestParam(value = "startDate", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(value = "endDate", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "sort", required = false, defaultValue = "desc") String sort,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(invoiceService.getInvoices(startDate, endDate, status, sort, page, size));
        log.info("Get Invoice [" + startDate + "|" + endDate + "]");
        return resultApi;
    }

    @GetMapping("/chart-data")
    public ResponseObject<?> getChartData(
            @RequestParam(value = "startDate", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam(value = "endDate", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @RequestParam(value = "status", required = false) String status
    ) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(invoiceService.getInvoiceChartData(startDate, endDate, status));
        log.info("Get ChartData [" + startDate + "|" + endDate + "]");
        return resultApi;
    }


}
