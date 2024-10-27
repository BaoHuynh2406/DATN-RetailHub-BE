package com.project.retailhub.data.mapper;

import com.project.retailhub.data.dto.response.Invoice.InvoiceItemResponse;
import com.project.retailhub.data.entity.InvoiceItem;
import com.project.retailhub.data.repository.ProductRepository;
import com.project.retailhub.exception.AppException;
import com.project.retailhub.exception.ErrorCode;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InvoiceItemMapper {
    //Chuyển từ entity sang response
    @Mapping(source = "productId", target = "productName", qualifiedByName = "getProductName")
    InvoiceItemResponse toInvoiceItemResponse(InvoiceItem invoiceItem, @Context ProductRepository productRepository);

    @Named("getProductName")
    default String mapToProductName(Long productId, @Context ProductRepository productRepository) {
        return productRepository.findById(productId).orElseThrow(
                () -> new AppException(ErrorCode.PRODUCT_NOT_AVAILABLE)
        ).getProductName();
    }

    //to List Invoice Item Response
    List<InvoiceItemResponse> toListInvoiceResponse(List<InvoiceItem> ls);
}
