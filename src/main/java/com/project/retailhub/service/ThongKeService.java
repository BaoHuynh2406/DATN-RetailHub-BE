package com.project.retailhub.service;

import com.project.retailhub.data.dto.response.Invoice.InvoiceChartDataResponse;
import com.project.retailhub.data.dto.response.ThongKe.SalesVolumeStatistics;

import java.util.Date;
import java.util.List;

public interface ThongKeService {
    //DS doanh thu, loi nhuan, thue tu A den B
    //Dữ liệu cho chart
    List<InvoiceChartDataResponse> getInvoiceChartDataStartToEnd(Date start, Date end, String status);

    //Thống kê luợt bán của các sản phầm
    List<SalesVolumeStatistics> getSalesVolumeStatistics(Date start, Date end, String sort, int page, int size);

}
