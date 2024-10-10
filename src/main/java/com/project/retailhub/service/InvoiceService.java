package com.project.retailhub.service;


import com.project.retailhub.data.dto.request.InvoiceRequest.InvoiceRequestCreate;
import com.project.retailhub.data.dto.response.Invoice.InvoiceResponseForUser;

import java.util.List;

public interface InvoiceService {

    //Lấy tất cả
    List<InvoiceResponseForUser> getAllListInvoiceByUserId(Long userId);

    //Lấy hóa đơn đang thanh toán
    List<InvoiceResponseForUser> getPendingListInvoiceByUserId(Long userId);

    //Lấy hóa đơn đã thanh toán
    List<InvoiceResponseForUser> getPaidListInvoiceByUserId(Long userId);

    //Bị hủy
    // viết sau
    //Hoàn trả
    // viết sau

    //Tạo Hóa đơn mới
    InvoiceResponseForUser createNewInvoice(InvoiceRequestCreate request);

    //Hủy hóa đơn
    void canceledInvoice(Long invoiceId);
}
