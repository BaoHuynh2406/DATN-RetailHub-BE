package com.project.retailhub.service.impelement;

import com.project.retailhub.data.dto.response.Invoice.InvoiceChartDataResponse;
import com.project.retailhub.data.dto.response.ThongKe.SalesVolumeStatistics;
import com.project.retailhub.data.mapper.InvoiceMapper;
import com.project.retailhub.data.repository.CustomerRepository;  // Đảm bảo có CustomerRepository
import com.project.retailhub.data.repository.InvoiceRepository;
import com.project.retailhub.data.repository.ProductRepository;
import com.project.retailhub.service.ThongKeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ThongKeSvImpl implements ThongKeService {
    InvoiceMapper invoiceMapper;
    InvoiceRepository invoiceRepository;
    private final ProductRepository productRepository;
    CustomerRepository customerRepository;  // Thêm repository để truy vấn số lượng khách hàng

    private Date normalizeStartDate(Date start) {
        return start != null ? start : new Date();
    }

    private Date normalizeEndDate(Date end) {
        if (end == null) end = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(end);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    private List<String> parseStatusList(String status) {
        return (status != null && !status.isEmpty())
                ? Arrays.asList(status.split(","))
                : null;
    }

    @Override
    public List<InvoiceChartDataResponse> getInvoiceChartDataStartToEnd(Date start, Date end, String status) {
        start = normalizeStartDate(start);
        end = normalizeEndDate(end);

        if (end.before(start)) {
            throw new RuntimeException("Ngay ket thuc phai sau ngay bat dau");
        }
        List<String> statusList = parseStatusList(status);

        return invoiceMapper.toInvoiceChartDataResponseList(
                invoiceRepository.findInvoicesBetweenDatesAndStatuses(start, end, statusList)
        );
    }

    @Override
    public List<SalesVolumeStatistics> getSalesVolumeStatistics(
            Date start, Date end, String sort
    )

    {
        start = normalizeStartDate(start);
        end = normalizeEndDate(end);

        if (end.before(start)) {
            throw new RuntimeException("Ngay ket thuc phai sau ngay bat dau");
        }
        // Truy vấn danh sách từ repository
        List<Object[]> results = productRepository.findProductSales(start, end, sort);

        // Ánh xạ kết quả từ danh sách Object[] sang DTO SalesVolumeStatistics
        return results.stream().map(result -> {
            SalesVolumeStatistics stats = new SalesVolumeStatistics();
            stats.setProductId(((Number) result[0]).longValue());
            stats.setProductName((String) result[1]);
            stats.setQuantitySold(((Number) result[2]).intValue());
            return stats;
        }).collect(Collectors.toList());
    }

    public Long getActiveCustomerCount() {
        return customerRepository.countByIsDeleteFalse();  // Đếm khách hàng đang hoạt động
    }

    @Override
    public Long getInvoiceCountByDateAndStatus(Date invoiceDate) {
        // Chuẩn hóa ngày bắt đầu và kết thúc
        Calendar calendar = Calendar.getInstance();

        // Ngày bắt đầu là 00:00 của ngày
        calendar.setTime(invoiceDate);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startOfDay = calendar.getTime();

        // Ngày kết thúc là 23:59 của ngày
        calendar.setTime(invoiceDate);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date endOfDay = calendar.getTime();

        return invoiceRepository.countInvoicesByDateAndStatus(startOfDay, endOfDay);
    }

    @Override
    public BigDecimal getRevenueByDateAndStatus(Date date) {
        // Chuẩn hóa ngày bắt đầu và kết thúc
        Calendar calendar = Calendar.getInstance();

        // Ngày bắt đầu là 00:00 của ngày
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startOfDay = calendar.getTime();

        // Ngày kết thúc là 23:59 của ngày
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date endOfDay = calendar.getTime();

        // Trả về tổng doanh thu bằng finalTotal
        return invoiceRepository.sumRevenueByDateAndStatus(startOfDay, endOfDay);
    }

    @Override
    public BigDecimal getRevenueByMonthAndStatus(Date month) {
        // Chuẩn hóa ngày bắt đầu và kết thúc của tháng
        Calendar calendar = Calendar.getInstance();

        // Ngày bắt đầu của tháng (00:00:00)
        calendar.setTime(month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date startOfMonth = calendar.getTime();

        // Ngày kết thúc của tháng (23:59:59)
        calendar.setTime(month);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date endOfMonth = calendar.getTime();

        // Trả về tổng doanh thu cho tháng
        return invoiceRepository.sumRevenueByMonthAndStatus(startOfMonth, endOfMonth);
    }


}