package com.project.retailhub.api.InvoiceAPI;

import com.project.retailhub.data.dto.request.InvoiceRequest.InvoiceItemRequest;
import com.project.retailhub.data.dto.request.InvoiceRequest.InvoiceRequestCreate;
import com.project.retailhub.data.dto.response.ResponseObject;
import com.project.retailhub.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
            @RequestParam(value = "startDate", required = true) Date startDate,
            @RequestParam(value = "endDate", required = true) Date endDate,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(invoiceService.getInvoices(startDate, endDate, page, size));
        log.info("Get Invoice ["+startDate+"|"+endDate+"]");
        return resultApi;
    }




}
