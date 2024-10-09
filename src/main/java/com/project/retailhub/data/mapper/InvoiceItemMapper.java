package com.project.retailhub.data.mapper;

import com.project.retailhub.data.dto.response.Invoice.InvoiceItemResponse;
import com.project.retailhub.data.entity.InvoiceItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InvoiceItemMapper {
    //Chuyển từ entity sang response
    InvoiceItemResponse toInvoiceItemResponse(InvoiceItem invoiceItem);

    //to List Invoice Item Response
    List<InvoiceItemResponse> toListInvoiceResponse(List<InvoiceItem> ls);
}
