package com.project.retailhub.api.InvoiceAPI;

import com.project.retailhub.data.dto.response.ResponseObject;
import com.project.retailhub.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/invoice")
public class invoiceAPIv1 {
    final InvoiceService invoiceService;

    @GetMapping("/getAllListInvoiceByUserId")
    public ResponseObject<?> getAllListInvoiceByUserId(
            @RequestParam(value = "userId", required = true) Long userId) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(invoiceService.getAllListInvoiceByUserId(userId));
        log.info("Get ALL Invoice");
        return resultApi;
    }

    @GetMapping("/getPendingListInvoiceByUserId")
    public ResponseObject<?> getPendingListInvoiceByUserId(
            @RequestParam(value = "userId", required = true) Long userId) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(invoiceService.getPendingListInvoiceByUserId(userId));
        log.info("Get ALL Invoice");
        return resultApi;
    }


    @GetMapping("/getPaidListInvoiceByUserId")
    public ResponseObject<?> getPaidListInvoiceByUserId(
            @RequestParam(value = "userId", required = true) Long userId
    ) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(invoiceService.getPaidListInvoiceByUserId(userId));
        log.info("Get ALL Invoice");
        return resultApi;
    }
}
