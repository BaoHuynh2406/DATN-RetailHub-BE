package com.project.retailhub.data.mapper;

import com.project.retailhub.data.dto.response.Invoice.InvoiceItemResponse;
import com.project.retailhub.data.dto.response.Invoice.InvoiceResponseForUser;
import com.project.retailhub.data.entity.Invoice;
import com.project.retailhub.data.entity.InvoiceItem;
import com.project.retailhub.data.repository.InvoiceItemRepository;
import com.project.retailhub.data.repository.ProductRepository;
import com.project.retailhub.exception.AppException;
import com.project.retailhub.exception.ErrorCode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Context;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {InvoiceItemMapper.class})
public abstract class InvoiceMapper {

    @Mapping(target = "listItem", ignore = true)  // ListItem được ánh xạ thủ công bên dưới
    public InvoiceResponseForUser toInvoiceResponseForUser(Invoice invoice,
                                                           @Context InvoiceItemRepository invoiceItemRepository,
                                                           @Context ProductRepository productRepository,
                                                           @Context InvoiceItemMapper invoiceItemMapper) {

        // Lấy các InvoiceItem từ repository
        List<InvoiceItem> invoiceItems = invoiceItemRepository.findByInvoiceId(invoice.getInvoiceId());

        // Ánh xạ InvoiceItem sang InvoiceItemResponse và bổ sung productName
        List<InvoiceItemResponse> listItem = invoiceItems.stream()
                .map(item -> {
                    InvoiceItemResponse itemResponse = invoiceItemMapper.toInvoiceItemResponse(item);

                    // Lấy productId từ item và tìm product
                    var product = productRepository.findById(itemResponse.getProductId())
                            .orElseThrow(() -> new AppException(ErrorCode.NO_PRODUCTS_FOUND));

                    // Gán productName vào itemResponse
                    itemResponse.setProductName(product.getProductName());

                    return itemResponse;
                }).collect(Collectors.toList());

        // Sử dụng builder pattern để tạo đối tượng InvoiceResponseForUser
        return InvoiceResponseForUser.builder()
                .invoiceId(invoice.getInvoiceId())
                .customerId(invoice.getCustomerId())
                .totalTax(invoice.getTotalTax())
                .totalPayment(invoice.getTotalPayment())
                .totalAmount(invoice.getTotalAmount())
                .listItem(listItem)
                .status(invoice.getStatus())
                .build();
    }

    // Phương thức để chuyển đổi danh sách Invoice sang danh sách InvoiceResponseForUser
    public List<InvoiceResponseForUser> toInvoiceResponseForUserList(List<Invoice> invoices,
                                                                     @Context InvoiceItemRepository invoiceItemRepository,
                                                                     @Context ProductRepository productRepository,
                                                                     @Context InvoiceItemMapper invoiceItemMapper) {
        return invoices.stream()
                .map(invoice -> toInvoiceResponseForUser(invoice, invoiceItemRepository, productRepository, invoiceItemMapper))
                .collect(Collectors.toList());
    }
}
