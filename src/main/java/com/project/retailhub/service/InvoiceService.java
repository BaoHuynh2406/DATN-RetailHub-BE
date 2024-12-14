package com.project.retailhub.service;


import com.project.retailhub.data.dto.request.InvoiceRequest.ExChangePoinstRq;
import com.project.retailhub.data.dto.request.InvoiceRequest.InvoiceItemRequest;
import com.project.retailhub.data.dto.request.InvoiceRequest.InvoiceRequestCreate;
import com.project.retailhub.data.dto.request.InvoiceRequest.UpdateCustomerInvoiceRq;
import com.project.retailhub.data.dto.response.Invoice.InvoiceChartDataResponse;
import com.project.retailhub.data.dto.response.Invoice.InvoiceItemResponse;
import com.project.retailhub.data.dto.response.Invoice.InvoiceResponse;
import com.project.retailhub.data.dto.response.Invoice.InvoiceResponseForUser;
import com.project.retailhub.data.dto.response.Pagination.PageResponse;
import com.project.retailhub.data.entity.InvoiceItem;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Date;
import java.util.List;

public interface InvoiceService {

    // Lấy hóa đơn theo Id
    InvoiceResponseForUser getInvoiceById(Long invoiceId);

    //Lấy tất cả theo nhân viên
    List<InvoiceResponseForUser> getAllListInvoiceByUserId(Long userId);

    //Lấy hóa đơn đang thanh toán theo nhân viên
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CS')")
    List<InvoiceResponseForUser> getPendingListInvoiceByUserCurrent();

    //Tìm kiếm
    PageResponse<InvoiceResponse> findInvoiceWithKeyWord(int page, int size, String keyword);

    //--------------PHỤC VỤ CHO VIỆC THÔNG KÊ--------------------------------------------------
    // Lấy hóa đơn từ ngày A -> B
    PageResponse<InvoiceResponse> getInvoices(Date start, Date end, String status, String sort, int page, int size);

    PageResponse<InvoiceResponse> getAllForUserCurrent(String status, String sort, int page, int size);
    //-----------------------------------------------------------------------------------------


    //Tạo Hóa đơn mới
    InvoiceResponseForUser createNewInvoice(InvoiceRequestCreate request);

    //Hủy hóa đơn
    void canceledInvoice(Long invoiceId);

    // Tăng số lượng sản phẩm trong hóa đơn
    void updateQuantity(Long invoiceId, Long invoiceItemId, Integer quantity);

    // Xóa sản phẩm khỏi hóa đơn
    void removeItem(Long invoiceId, Long invoiceItemId);

    //Thêm mới sản phẩm vào hóa đơn
    InvoiceItemResponse createNewInvoiceItem(InvoiceItemRequest createRequest);

    //Thay đổi khách hàng cho hóa đơn
    void updateCustomer(UpdateCustomerInvoiceRq rq);

    //Đổi điểm
    void exchangePoints(ExChangePoinstRq exchangePointsRequest);


}
