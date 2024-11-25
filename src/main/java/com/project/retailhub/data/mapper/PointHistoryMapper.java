package com.project.retailhub.data.mapper;

import com.project.retailhub.data.dto.request.HistoryRequest;
import com.project.retailhub.data.dto.response.HistoryResponse;
import com.project.retailhub.data.entity.PointHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PointHistoryMapper {
    PointHistoryMapper INSTANCE = Mappers.getMapper(PointHistoryMapper.class);

    // Chuyển từ Entity -> DTO Response
    @Mapping(source = "customerId", target = "customerId")
    @Mapping(source = "transactionDate", target = "transactionDate", dateFormat = "yyyy-MM-dd")
    HistoryResponse toResponse(PointHistory entity);

    // Chuyển từ DTO Request -> Entity
    @Mapping(source = "customerId", target = "customerId")
    @Mapping(source = "transactionDate", target = "transactionDate", dateFormat = "yyyy-MM-dd")
    PointHistory toEntity(HistoryRequest request);
}
