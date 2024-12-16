package com.project.retailhub.api.InvoiceAPI;

import com.project.retailhub.data.dto.request.InvoiceRequest.ExChangePoinstRq;
import com.project.retailhub.data.dto.request.InvoiceRequest.InvoiceItemRequest;
import com.project.retailhub.data.dto.request.InvoiceRequest.InvoiceRequestCreate;
import com.project.retailhub.data.dto.request.InvoiceRequest.UpdateCustomerInvoiceRq;
import com.project.retailhub.data.dto.response.ResponseObject;
import com.project.retailhub.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/invoice")
public class invoiceAPIv1 {
    final InvoiceService invoiceService;

//    API này sẽ lấy tất cả hóa đơn của người dùng có userId cụ thể.
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CS')")
    @GetMapping("/getAllListInvoiceByUserId")
    public ResponseObject<?> getAllListInvoiceByUserId(
            @RequestParam(value = "userId", required = true) Long userId) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(invoiceService.getAllListInvoiceByUserId(userId));
        log.info("Get ALL Invoice");

//      Trả về danh sách các hóa đơn của người dùng.
        return resultApi;
    }

//    API này sẽ lấy thông tin chi tiết của một hóa đơn cụ thể theo invoiceId.
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CS')")
    @GetMapping("/get/{invoiceId}")
    public ResponseObject<?> getInvoiceById(
            @PathVariable("invoiceId") long invoiceId) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(invoiceService.getInvoiceById(invoiceId));
        log.info("Get Invoice ID:" + invoiceId);

//      Trả về thông tin hóa đơn tương ứng với invoiceId.
        return resultApi;
    }

//    API này sẽ lấy tất cả các hóa đơn đang chờ xử lý của người dùng hiện tại.
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CS')")
    @GetMapping("/getPendingListInvoice")
    public ResponseObject<?> getPendingListInvoiceByUserCurrent() {
        var resultApi = new ResponseObject<>();
        resultApi.setData(invoiceService.getPendingListInvoiceByUserCurrent());
        log.info("Get ALL Invoice");

//      Trả về danh sách hóa đơn đang chờ.
        return resultApi;
    }

//    API này sẽ tạo một hóa đơn mới cho người dùng.
//    Dữ liệu cần thiết được gửi qua body của yêu cầu dưới dạng InvoiceRequestCreate.
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CS')")
    @PostMapping("/create")
    public ResponseObject<?> postCreate(
            @RequestBody InvoiceRequestCreate request
    ) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(invoiceService.createNewInvoice(request));
        log.info("Create new invoice for user id: " + request.getUserId());

//      Trả về thông tin hóa đơn vừa được tạo.
        return resultApi;
    }

//    API này sẽ hủy một hóa đơn cụ thể theo invoiceId.
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CS')")
    @DeleteMapping("/cancel-invoice/{invoiceId}")
    public ResponseObject<?> cancelInvoice(
            @PathVariable("invoiceId") Long invoiceId
    ) {
        var resultApi = new ResponseObject<>();
        invoiceService.canceledInvoice(invoiceId);
        log.info("Cancel Invoice with id: " + invoiceId);

//      Trả về thông báo rằng hóa đơn đã được hủy.
        return resultApi;
    }

//    API này sẽ cập nhật số lượng của một sản phẩm trong hóa đơn.
//    Các tham số invoiceId, invoiceItemId và quantity được truyền qua yêu cầu.
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CS')")
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

//      Trả về thông báo rằng số lượng đã được cập nhật thành công.
        return resultApi;
    }

//    API này sẽ xóa một sản phẩm khỏi hóa đơn dựa trên invoiceId và invoiceItemId.
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CS')")
    @DeleteMapping("/remove-item")
    public ResponseObject<?> removeItem(
            @RequestParam("invoiceId") Long invoiceId,
            @RequestParam("invoiceItemId") Long invoiceItemId
    ) {
        var resultApi = new ResponseObject<>();
        invoiceService.removeItem(invoiceId, invoiceItemId);
        log.info("Removed item: " + invoiceItemId + " from invoice: " + invoiceId);
        resultApi.setMessage("Item removed successfully.");

//      Trả về thông báo rằng sản phẩm đã được xóa thành công.
        return resultApi;
    }

//    API này sẽ thêm một sản phẩm mới vào hóa đơn.
//    Dữ liệu về sản phẩm được gửi qua body dưới dạng InvoiceItemRequest.
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CS')")
    @PostMapping("/add-new-item")
    public ResponseObject<?> addNewItem(
            @RequestBody InvoiceItemRequest request
    ) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(invoiceService.createNewInvoiceItem(request));
        log.info("Added new item to invoice: " + request.getInvoiceId());

//      Trả về thông tin của sản phẩm mới được thêm vào hóa đơn.
        return resultApi;
    }


//    API này sẽ cập nhật thông tin khách hàng cho một hóa đơn cụ thể.
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CS')")
    @PutMapping("/update-custom-invoice")
    public ResponseObject<?> updateCustomer(
            @RequestBody UpdateCustomerInvoiceRq request
    ) {
        var resultApi = new ResponseObject<>();
        invoiceService.updateCustomer(request);
        resultApi.setData("success");
        log.info("Updated invoice: " + request.getInvoiceId());

//      Trả về thông báo thành công.
        return resultApi;
    }

//    API này sẽ xử lý yêu cầu đổi điểm của khách hàng,
//    với dữ liệu gửi qua body dưới dạng ExChangePoinstRq.
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CS')")
    @PutMapping("/exchange-points")
    public ResponseObject<?> exchangePoints(
            @RequestBody ExChangePoinstRq request
    ) {
        var resultApi = new ResponseObject<>();
        invoiceService.exchangePoints(request);
        resultApi.setData("success");
        log.info("Exchange points of customerId:" + request.getCustomerId());

//      Trả về thông báo thành công khi đổi điểm thành công
        return resultApi;
    }

}
