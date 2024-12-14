package com.project.retailhub.service;

import com.project.retailhub.data.dto.response.Invoice.InvoiceChartDataResponse;
import com.project.retailhub.data.dto.response.Pagination.PageResponse;
import com.project.retailhub.data.dto.response.ThongKe.RevenueProfitResponse;
import com.project.retailhub.data.dto.response.ThongKe.SalesVolumeStatistics;
import com.project.retailhub.data.entity.Product;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ThongKeService {
    // DS doanh thu, lợi nhuận, thuế từ A đến B
    List<InvoiceChartDataResponse> getInvoiceChartDataStartToEnd(Date start, Date end, String status);

    // Thống kê lượt bán của các sản phẩm
    PageResponse<SalesVolumeStatistics> getSalesVolumeStatistics(int page, int size, Date start, Date end);

    // Số lượng khách hàng hoạt động
    Long getActiveCustomerCount();

    // Đếm số lượng hóa đơn theo ngày và trạng thái PAID (bao gồm từ 00:00 đến 23:59)
    Long getInvoiceCountByDateAndStatus(Date invoiceDate);

    // Tính doanh thu cho một ngày và trạng thái PAID
    BigDecimal getRevenueByDateAndStatus(Date date);

    // Tính doanh thu cho tháng và trạng thái PAID
    BigDecimal getRevenueByMonthAndStatus(Date month);


    // Tính doanh thu Năm
    List<RevenueProfitResponse> getRevenueAndProfitByYear(int year);

    // Lấy top 5 sản phẩm có lượng tồn kho thấp nhất
    List<Product> getTop5LowestInventoryCountProducts();
}
