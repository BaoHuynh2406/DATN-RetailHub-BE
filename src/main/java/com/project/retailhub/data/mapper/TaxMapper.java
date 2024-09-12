package com.project.retailhub.data.mapper;

import com.project.retailhub.data.dto.request.Tax.TaxRequest;
import com.project.retailhub.data.dto.response.Tax.TaxResponse;
import com.project.retailhub.data.entity.Tax;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaxMapper {
    // Request to Entity
    Tax toTax(TaxRequest request);

    // Entity to Response
    TaxResponse toTaxResponse(Tax tax);

    // Phương thức chuyển đổi danh sách Tax thành danh sách TaxResponse
    List<TaxResponse> toTaxResponseList(List<Tax> taxes);

    // Phương thức chuyển đổi danh sách TaxRequest thành danh sách Tax
    List<Tax> toTaxesList(List<TaxRequest> requests);


}
