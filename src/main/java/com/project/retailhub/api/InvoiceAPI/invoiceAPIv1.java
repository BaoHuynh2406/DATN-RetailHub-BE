package com.project.retailhub.api.InvoiceAPI;

import com.project.retailhub.data.dto.request.InvoiceRequest.ExChangePoinstRq;
import com.project.retailhub.data.dto.request.InvoiceRequest.InvoiceItemRequest;
import com.project.retailhub.data.dto.request.InvoiceRequest.InvoiceRequestCreate;
import com.project.retailhub.data.dto.request.InvoiceRequest.UpdateCustomerInvoiceRq;
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

    // api cho quan ly
    @GetMapping("/getAllListInvoiceByUserId")
    public ResponseObject<?> getAllListInvoiceByUserId(
            @RequestParam(value = "userId", required = true) Long userId) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(invoiceService.getAllListInvoiceByUserId(userId));
        log.info("Get ALL Invoice");
        return resultApi;
    }

    //api cho user
    //get bang token...



    @GetMapping("/get/{invoiceId}")
    public ResponseObject<?> getInvoiceById(
            @PathVariable("invoiceId") long invoiceId) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(invoiceService.getInvoiceById(invoiceId));
        log.info("Get Invoice ID:" + invoiceId);
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
            @RequestParam("quantity") Integer quantity
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

    @PostMapping("/add-new-item")
    public ResponseObject<?> addNewItem(
            @RequestBody InvoiceItemRequest request
    ) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(invoiceService.createNewInvoiceItem(request));
        log.info("Added new item to invoice: " + request.getInvoiceId());
        return resultApi;
    }


    //Cập nhật khách hàng cho hóa đơn
    @PutMapping("/update-custom-invoice")
    public ResponseObject<?> updateCustomer(
            @RequestBody UpdateCustomerInvoiceRq request
    ) {
        var resultApi = new ResponseObject<>();
        invoiceService.updateCustomer(request);
        resultApi.setData("success");
        log.info("Updated invoice: " + request.getInvoiceId());
        return resultApi;
    }

    //Đổi điểm
    @PutMapping("/exchange-points")
    public ResponseObject<?> exchangePoints(
            @RequestBody ExChangePoinstRq request
    ) {
        var resultApi = new ResponseObject<>();
        invoiceService.exchangePoints(request);
        resultApi.setData("success");
        log.info("Exchange points of customerId:" + request.getCustomerId());
        return resultApi;
    }

}
