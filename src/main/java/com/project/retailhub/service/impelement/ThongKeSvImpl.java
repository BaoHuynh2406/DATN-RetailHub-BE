package com.project.retailhub.service.impelement;

import com.project.retailhub.data.dto.response.Invoice.InvoiceChartDataResponse;
import com.project.retailhub.data.dto.response.ThongKe.SalesVolumeStatistics;
import com.project.retailhub.data.mapper.InvoiceMapper;
import com.project.retailhub.data.repository.InvoiceRepository;
import com.project.retailhub.service.ThongKeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

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

    private Date normalizeStartDate(Date start) {
        return start != null ? start : new Date();
    }

    /**
     * Chuẩn hóa ngày kết thúc, đặt giờ phút giây tối đa trong ngày.
     */
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

    /**
     * Phân tách trạng thái từ chuỗi thành danh sách.
     */
    private List<String> parseStatusList(String status) {
        return (status != null && !status.isEmpty())
                ? Arrays.asList(status.split(","))
                : null;
    }

    @Override
    public List<InvoiceChartDataResponse> getInvoiceChartDataStartToEnd(Date start, Date end, String status) {
        // Chuẩn hóa ngày bắt đầu và kết thúc
        start = normalizeStartDate(start);
        end = normalizeEndDate(end);

        if (end.before(start)) {
            throw new RuntimeException("Ngay ket thuc phai sau ngay bat dau");
        }
        // Phân tách trạng thái thành danh sách
        List<String> statusList = parseStatusList(status);

        // Truy vấn dữ liệu và ánh xạ kết quả
        return invoiceMapper.toInvoiceChartDataResponseList(
                invoiceRepository.findInvoicesBetweenDatesAndStatuses(start, end, statusList)
        );
    }

    @Override
    public List<SalesVolumeStatistics> getSalesVolumeStatistics(Date start, Date end, String sort, int page, int size) {
        // Chuẩn hóa ngày bắt đầu và kết thúc
        start = normalizeStartDate(start);
        end = normalizeEndDate(end);

        if (end.before(start)) {
            throw new RuntimeException("Ngày kết thúc phải sau ngày bắt đầu");
        }

        // Trạng thái hóa đơn cần truy vấn
        String status = "PAID";

        // Truy vấn danh sách từ repository
        List<Object[]> results = invoiceRepository.findSoldProductsBetweenDatesWithStatus(start, end, sort, page, size);

        // Ánh xạ kết quả từ danh sách Object[] sang DTO SalesVolumeStatistics
        return results.stream().map(result -> {
            SalesVolumeStatistics stats = new SalesVolumeStatistics();
            stats.setInvoiceId(((Number) result[0]).longValue());
            stats.setProductName((String) result[1]);
            stats.setQuantitySold(((Number) result[2]).intValue());
            return stats;
        }).collect(Collectors.toList());
    }
}