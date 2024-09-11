package com.project.retailhub.data.mapper;

import com.project.retailhub.data.dto.request.TaxRequest;
import com.project.retailhub.data.dto.request.UserRequest;
import com.project.retailhub.data.dto.response.TaxResponse;
import com.project.retailhub.data.dto.response.UserResponse;
import com.project.retailhub.data.entity.Tax;
import com.project.retailhub.data.entity.User;
import com.project.retailhub.data.repository.TaxRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaxMapper {

    Tax toTax(TaxRequest request);

    Tax toTaxResponse(TaxResponse response);

    List<TaxResponse> toTaxResponseList(List<Tax> taxes);

    List<Tax> toTaxesList(List<TaxRequest> requests);

}
