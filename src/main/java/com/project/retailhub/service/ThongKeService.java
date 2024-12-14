package com.project.retailhub.service;

import com.project.retailhub.data.dto.response.Invoice.InvoiceChartDataResponse;
import com.project.retailhub.data.dto.response.ThongKe.SalesVolumeStatistics;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ThongKeService {
    //DS doanh thu, loi nhuan, thue tu A den B
    //Dữ liệu cho chart
    List<InvoiceChartDataResponse> getInvoiceChartDataStartToEnd(Date start, Date end, String status);

    //Thống kê luợt bán của các sản phầm
    List<SalesVolumeStatistics> getSalesVolumeStatistics(Date start, Date end, String sort);

    //Pt mới để thêm số lượng khách hàng
    Long getActiveCustomerCount();

    // Đếm số lượng hóa đơn theo ngày và trạng thái PAID (bao gồm từ 00:00 đến 23:59)
    Long getInvoiceCountByDateAndStatus(Date invoiceDate);

    // Tính doanh thu cho một ngày và trạng thái PAID
    BigDecimal getRevenueByDateAndStatus(Date date);

    // Tính doanh thu cho tháng và trạng thái PAID
    BigDecimal getRevenueByMonthAndStatus(Date month);
}
