package com.project.retailhub.data.mapper;

import com.project.retailhub.data.dto.request.TransactionRequest;
import com.project.retailhub.data.dto.response.HistoryResponse;
import com.project.retailhub.data.entity.PointHistory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PointHistoryMapper {

    // Chuyển từ Entity -> DTO Response
    HistoryResponse toResponse(PointHistory entity);

    // Chuyển từ DTO Request -> Entity
    PointHistory toEntity(TransactionRequest request);
}
