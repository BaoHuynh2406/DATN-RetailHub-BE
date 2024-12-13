package com.project.retailhub.service;

import com.project.retailhub.data.dto.response.Invoice.InvoiceChartDataResponse;

import java.util.Date;
import java.util.List;

public interface ThongKeService {
    //DS doanh thu, loi nhuan, thue tu A den B
    //Dữ liệu cho chart
    List<InvoiceChartDataResponse> getInvoiceChartDataStartToEnd(Date start, Date end, String status);
    //Pt mới để thêm số lượng khách hàng
    Long getActiveCustomerCount();
    // Đếm số lượng hóa đơn theo ngày và trạng thái
    Long getInvoiceCountByDateAndStatus(Date invoiceDate, List<String> statuses);
}
