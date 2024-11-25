package com.project.retailhub.service.impelement;

import com.project.retailhub.data.dto.request.HistoryRequest;
import com.project.retailhub.data.dto.response.HistoryResponse;
import com.project.retailhub.data.entity.PointHistory;
import com.project.retailhub.data.mapper.PointHistoryMapper;
import com.project.retailhub.data.repository.PointHistoryRepository;
import com.project.retailhub.service.PointHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class PointHistoryServiceImpl implements PointHistoryService {

    private final PointHistoryRepository repository;
    private final PointHistoryMapper mapper;

    @Override
    public Page<HistoryResponse> getAllHistories(int page, int size) {
        return repository.findAll(PageRequest.of(page - 1, size))
                .map(mapper::toResponse);
    }

    @Override
    public Page<HistoryResponse> getHistoriesByCustomerId(Long customerId, int page, int size) {
        return repository.findByCustomerId(customerId, PageRequest.of(page - 1, size))
                .map(mapper::toResponse);
    }

    @Override
    public HistoryResponse createHistory(HistoryRequest request) {
        PointHistory entity = mapper.toEntity(request);
        PointHistory savedEntity = repository.save(entity);
        return mapper.toResponse(savedEntity);
    }

    @Override
    public HistoryResponse exchangePoints(Long customerId, int pointsToExchange, String description) {
        // Kiểm tra điều kiện đổi điểm
        if (pointsToExchange <= 0) {
            throw new IllegalArgumentException("Số điểm đổi phải lớn hơn 0.");
        }

        // Tạo giao dịch đổi điểm
        PointHistory exchangeTransaction = PointHistory.builder()
                .customerId(customerId)
                .points(-pointsToExchange) // Điểm trừ
                .transactionType("EXCHANGE")
                .description(description)
                .transactionDate(new Date())
                .build();

        // Lưu vào cơ sở dữ liệu
        PointHistory savedTransaction = repository.save(exchangeTransaction);

        // Chuyển đổi thành DTO Response
        return mapper.toResponse(savedTransaction);
    }
}
