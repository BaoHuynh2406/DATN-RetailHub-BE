package com.project.retailhub.api.InvoiceAPI;

import com.project.retailhub.data.dto.request.InvoiceRequest.InvoiceRequestCreate;
import com.project.retailhub.data.dto.response.ResponseObject;
import com.project.retailhub.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/create")
    public ResponseObject<?> postCreate(
            @RequestBody InvoiceRequestCreate request
    ) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(invoiceService.createNewInvoice(request));
        log.info("Create new invoice for user id: " + request.getUserId());

        return resultApi;
    }

    @DeleteMapping("/cancel-invoice/{invoiceId}")
    public ResponseObject<?> cancelInvoice(
            @PathVariable("invoiceId") Long invoiceId
    ) {
        var resultApi = new ResponseObject<>();
        invoiceService.canceledInvoice(invoiceId);
        log.info("Cancel Invoice with id: " + invoiceId);
        return resultApi;
    }

    @PostMapping("/update-quantity")
    public ResponseObject<?> updateQuantity(
            @RequestParam("invoiceId") Long invoiceId,
            @RequestParam("invoiceItemId") Long invoiceItemId,
            @RequestParam("quantity") Integer quantity // Số lượng cần cập nhật
    ) {
        var resultApi = new ResponseObject<>();
        invoiceService.updateQuantity(invoiceId, invoiceItemId, quantity);
        log.info("Updated quantity for item: " + invoiceItemId + " in invoice: " + invoiceId + " to " + quantity);
        resultApi.setMessage("Quantity updated successfully.");
        return resultApi;
    }

    @DeleteMapping("/remove-item")
    public ResponseObject<?> removeItem(
            @RequestParam("invoiceId") Long invoiceId,
            @RequestParam("invoiceItemId") Long invoiceItemId
    ) {
        var resultApi = new ResponseObject<>();
        invoiceService.removeItem(invoiceId, invoiceItemId);
        log.info("Removed item: " + invoiceItemId + " from invoice: " + invoiceId);
        resultApi.setMessage("Item removed successfully.");
        return resultApi;
    }

}
