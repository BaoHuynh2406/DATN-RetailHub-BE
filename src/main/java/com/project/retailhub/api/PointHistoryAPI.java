package com.project.retailhub.api;

import com.project.retailhub.data.dto.request.TransactionRequest;
import com.project.retailhub.data.dto.response.ResponseObject;
import com.project.retailhub.service.PointHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/point-history")
public class PointHistoryAPI {

    final PointHistoryService pointHistoryService;

    // API để lấy toàn bộ lịch sử điểm với phân trang
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/history-list")
    public ResponseObject<?> doGetAllHistories(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(pointHistoryService.getAllHistories(page, size));
        log.info("Fetched all point histories with page {} and size {}", page, size); // Ghi log chi tiết
        return resultApi;
    }

    // API để lấy lịch sử điểm theo khách hàng với phân trang
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/history-list/{customerId}")
    public ResponseObject<?> doGetHistoriesByCustomerId(
            @PathVariable Long customerId,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(pointHistoryService.getHistoriesByCustomerId(customerId, page, size));
        log.info("Fetched point histories for customer ID {} with page {} and size {}", customerId, page, size);
        return resultApi;
    }

    // API để tạo mới một lịch sử điểm
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CS')")
    @PostMapping("/exchange")
    public ResponseObject<?> doCreateExchange(@RequestBody TransactionRequest request) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(pointHistoryService.createTransaction(request));
        log.info("Created new point history for user ID {} and customer ID {}", request.getUserId(), request.getCustomerId());
        return resultApi;
    }
}
