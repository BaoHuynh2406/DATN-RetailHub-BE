package com.project.retailhub.service;

import com.project.retailhub.data.dto.request.HistoryRequest;
import com.project.retailhub.data.dto.response.HistoryResponse;
import org.springframework.data.domain.Page;

public interface PointHistoryService {

    Page<HistoryResponse> getAllHistories(int page, int size);

    Page<HistoryResponse> getHistoriesByCustomerId(Long customerId, int page, int size);

    HistoryResponse createHistory(HistoryRequest request);

    HistoryResponse exchangePoints(Long customerId, int pointsToExchange, String description); // Mới thêm
}