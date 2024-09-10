package com.project.retailhub.data.mapper;

import com.project.retailhub.data.dto.request.TaxRequest;
import com.project.retailhub.data.entity.Tax;
import com.project.retailhub.data.repository.TaxRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaxMapper {

    @Mapping(source = "taxId", target = "tax", qualifiedByName = "")
    Tax toTax(TaxRequest request, @Context TaxRepository taxRepository);

}
