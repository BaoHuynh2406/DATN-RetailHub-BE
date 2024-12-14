package com.project.retailhub.api.InvoiceAPI;

import com.project.retailhub.data.dto.request.InvoiceRequest.InvoiceItemRequest;
import com.project.retailhub.data.dto.request.InvoiceRequest.InvoiceRequestCreate;
import com.project.retailhub.data.dto.response.ResponseObject;
import com.project.retailhub.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v2/invoice")
public class invoiceAPIv2 {
    final InvoiceService invoiceService;

    @PreAuthorize("hasAuthority('ADMIN')")
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

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CS')")
    @GetMapping("/list-for-user-current-all")
    public ResponseObject<?> getListForUserAll(
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "sort", required = false, defaultValue = "desc") String sort,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(invoiceService.getAllForUserCurrent(status, sort, page, size));
        log.info("Get getListForUserAll");
        return resultApi;
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CS')")
    @GetMapping("/search")
    public ResponseObject<?> search(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "keyword", required = false) String keyword
    ) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(invoiceService.findInvoiceWithKeyWord(page, size, keyword));
        log.info("Get getListForUserAll");
        return resultApi;
    }
}
