package com.project.retailhub.service.impl;

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

@Service
@RequiredArgsConstructor
public class PointHistoryServiceImpl implements PointHistoryService {

    private final PointHistoryRepository repository;

    @Override
    public Page<HistoryResponse> getAllHistories(int page, int size) {
        return repository.findAll(PageRequest.of(page - 1, size))
                .map(PointHistoryMapper.INSTANCE::toResponse);
    }

    @Override
    public Page<HistoryResponse> getHistoriesByCustomerId(Long customerId, int page, int size) {
        return repository.findByCustomerId(customerId, PageRequest.of(page - 1, size))
                .map(PointHistoryMapper.INSTANCE::toResponse);
    }

    @Override
    public HistoryResponse createHistory(HistoryRequest request) {
        PointHistory entity = PointHistoryMapper.INSTANCE.toEntity(request);
        PointHistory savedEntity = repository.save(entity);
        return PointHistoryMapper.INSTANCE.toResponse(savedEntity);
    }
}
