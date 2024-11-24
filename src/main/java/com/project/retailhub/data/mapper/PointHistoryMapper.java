package com.project.retailhub.data.mapper;

import com.project.retailhub.data.dto.request.HistoryRequest;
import com.project.retailhub.data.dto.response.HistoryResponse;
import com.project.retailhub.data.entity.PointHistory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PointHistoryMapper {
    PointHistoryMapper INSTANCE = Mappers.getMapper(PointHistoryMapper.class);

    // Chuyển từ Entity -> DTO Response
    HistoryResponse toResponse(PointHistory entity);

    // Chuyển từ DTO Request -> Entity
    PointHistory toEntity(HistoryRequest request);
}
