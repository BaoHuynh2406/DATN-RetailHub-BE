package com.project.retailhub.service;

import com.project.retailhub.data.dto.request.HistoryRequest;
import com.project.retailhub.data.dto.response.HistoryResponse;
import com.project.retailhub.data.dto.response.Pagination.PageResponse;

public interface PointHistoryService {

    PageResponse<HistoryResponse> getAllHistories(int page, int size);

    PageResponse<HistoryResponse> getHistoriesByCustomerId(Long customerId, int page, int size);

    HistoryResponse createHistory(HistoryRequest request);
}