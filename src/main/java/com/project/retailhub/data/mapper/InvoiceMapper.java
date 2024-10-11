package com.project.retailhub.data.mapper;

import com.project.retailhub.data.dto.response.Invoice.InvoiceItemResponse;
import com.project.retailhub.data.dto.response.Invoice.InvoiceResponseForUser;
import com.project.retailhub.data.entity.Invoice;
import com.project.retailhub.data.entity.InvoiceItem;
import com.project.retailhub.data.repository.InvoiceItemRepository;
import com.project.retailhub.data.repository.ProductRepository;
import com.project.retailhub.exception.AppException;
import com.project.retailhub.exception.ErrorCode;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {InvoiceItemMapper.class})
public abstract class InvoiceMapper {

    @Mapping(target = "listItem", ignore = true)
    public abstract InvoiceResponseForUser toInvoiceResponseForUser(Invoice invoice,
                                                                    @Context InvoiceItemRepository invoiceItemRepository,
                                                                    @Context ProductRepository productRepository,
                                                                    @Context InvoiceItemMapper invoiceItemMapper);

    // Phương thức để chuyển đổi danh sách Invoice sang danh sách InvoiceResponseForUser
    public List<InvoiceResponseForUser> toInvoiceResponseForUserList(List<Invoice> invoices,
                                                                     @Context InvoiceItemRepository invoiceItemRepository,
                                                                     @Context ProductRepository productRepository,
                                                                     @Context InvoiceItemMapper invoiceItemMapper) {
        return invoices.stream()
                .map(invoice -> toInvoiceResponseForUser(invoice, invoiceItemRepository, productRepository, invoiceItemMapper))
                .collect(Collectors.toList());
    }

    @AfterMapping
    protected void mapListItem(@MappingTarget InvoiceResponseForUser response,
                               Invoice invoice,
                               @Context InvoiceItemRepository invoiceItemRepository,
                               @Context ProductRepository productRepository,
                               @Context InvoiceItemMapper invoiceItemMapper) {
        // Lấy các InvoiceItem từ repository
        List<InvoiceItem> invoiceItems = invoiceItemRepository.findByInvoiceId(invoice.getInvoiceId());

        // Ánh xạ InvoiceItem sang InvoiceItemResponse và bổ sung productName
        List<InvoiceItemResponse> listItem = invoiceItems.stream()
                .map(item -> {
                    // Chuyển đổi từ InvoiceItem sang InvoiceItemResponse
                    InvoiceItemResponse itemResponse = invoiceItemMapper.toInvoiceItemResponse(item);

                    // Lấy productId từ item và tìm product
                    var product = productRepository.findById(itemResponse.getProductId())
                            .orElseThrow(() -> new AppException(ErrorCode.NO_PRODUCTS_FOUND));

                    // Gán productName vào itemResponse
                    itemResponse.setProductName(product.getProductName());

                    return itemResponse;
                }).collect(Collectors.toList());

        // Gán listItem vào response
        response.setListItem(listItem);
    }
}
