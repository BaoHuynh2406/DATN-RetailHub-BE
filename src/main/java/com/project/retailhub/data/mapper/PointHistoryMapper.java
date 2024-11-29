package com.project.retailhub.data.mapper;

import com.project.retailhub.data.dto.request.HistoryRequest;
import com.project.retailhub.data.dto.response.HistoryResponse;
import com.project.retailhub.data.entity.PointHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PointHistoryMapper {

    // Chuyển từ Entity -> DTO Response
    HistoryResponse toResponse(PointHistory entity);

    // Chuyển từ DTO Request -> Entity
    PointHistory toEntity(HistoryRequest request);
}
