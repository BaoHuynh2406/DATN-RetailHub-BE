package com.project.retailhub.data.mapper;

import com.project.retailhub.data.dto.response.Invoice.InvoiceChartDataResponse;
import com.project.retailhub.data.dto.response.Invoice.InvoiceItemResponse;
import com.project.retailhub.data.dto.response.Invoice.InvoiceResponse;
import com.project.retailhub.data.dto.response.Invoice.InvoiceResponseForUser;
import com.project.retailhub.data.entity.Customer;
import com.project.retailhub.data.entity.Invoice;
import com.project.retailhub.data.entity.InvoiceItem;
import com.project.retailhub.data.entity.User;
import com.project.retailhub.data.repository.CustomerRepository;
import com.project.retailhub.data.repository.InvoiceItemRepository;
import com.project.retailhub.data.repository.ProductRepository;
import com.project.retailhub.data.repository.UserRepository;
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
                                                           @Context InvoiceItemMapper invoiceItemMapper,
                                                           @Context UserRepository userRepository,
                                                           @Context CustomerRepository customerRepository) {

        // Lấy các InvoiceItem từ repository) {

        // Lấy các InvoiceItem từ repository) {

        // Lấy các InvoiceItem từ repository
        List<InvoiceItem> invoiceItems = invoiceItemRepository.findByInvoiceId(invoice.getInvoiceId());

        // Ánh xạ InvoiceItem sang InvoiceItemResponse và bổ sung productName
        List<InvoiceItemResponse> listItem = invoiceItems.stream()
                .map(item -> {
                    InvoiceItemResponse itemResponse = invoiceItemMapper.toInvoiceItemResponse(item, productRepository);

                    // Lấy productId từ item và tìm product
                    var product = productRepository.findById(itemResponse.getProductId())
                            .orElseThrow(() -> new AppException(ErrorCode.NO_PRODUCTS_FOUND));

                    // Gán productName vào itemResponse
                    itemResponse.setProductName(product.getProductName());

                    return itemResponse;
                }).collect(Collectors.toList());

        //Lay ten nhan vien
        User user = userRepository.findById(invoice.getUserId())
                .get();
        //Lay ten khach hang
        Customer customer = customerRepository.findById(invoice.getCustomerId()).get();

        // Sử dụng builder pattern để tạo đối tượng InvoiceResponseForUser
        return InvoiceResponseForUser.builder()
                .invoiceId(invoice.getInvoiceId())
                .customerId(invoice.getCustomerId())
                .customerName(customer.getFullName())
                .userFullName(user.getFullName())
                .userId(invoice.getUserId())
                .totalTax(invoice.getTotalTax())
                .totalPayment(invoice.getTotalPayment())
                .totalAmount(invoice.getTotalAmount())
                .discountAmount(invoice.getDiscountAmount())
                .finalTotal(invoice.getFinalTotal())
                .listItem(listItem)
                .invoiceDate(invoice.getInvoiceDate())
                .status(invoice.getStatus())
                .build();
    }

    // Phương thức để chuyển đổi danh sách Invoice sang danh sách InvoiceResponseForUser
    public List<InvoiceResponseForUser> toInvoiceResponseForUserList(List<Invoice> invoices,
                                                                     @Context InvoiceItemRepository invoiceItemRepository,
                                                                     @Context ProductRepository productRepository,
                                                                     @Context InvoiceItemMapper invoiceItemMapper,
                                                                     @Context UserRepository userRepository,
                                                                     @Context CustomerRepository customerRepository) {
        return invoices.stream()
                .map(invoice -> toInvoiceResponseForUser(invoice, invoiceItemRepository, productRepository, invoiceItemMapper, userRepository, customerRepository))
                .collect(Collectors.toList());
    }

    // Phương thức để chuyển đổi Invoice sang InvoiceResponse
    public abstract InvoiceResponse toInvoiceResponse(Invoice invoice);

    public abstract List<InvoiceResponse> toInvoiceResponseList(List<Invoice> invoices);

    public abstract InvoiceChartDataResponse toInvoiceChartDataResponse(Invoice invoice);

    public abstract List<InvoiceChartDataResponse> toInvoiceChartDataResponseList(List<Invoice> invoices);

}
