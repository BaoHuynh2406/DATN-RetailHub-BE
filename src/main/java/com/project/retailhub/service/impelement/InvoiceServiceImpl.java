package com.project.retailhub.service.impelement;

import com.project.retailhub.data.dto.request.InvoiceRequest.InvoiceRequestCreate;
import com.project.retailhub.data.dto.response.Invoice.InvoiceItemResponse;
import com.project.retailhub.data.dto.response.Invoice.InvoiceResponseForUser;
import com.project.retailhub.data.entity.Invoice;
import com.project.retailhub.data.entity.InvoiceItem;
import com.project.retailhub.data.mapper.InvoiceItemMapper;
import com.project.retailhub.data.mapper.ProductMapper;
import com.project.retailhub.data.repository.InvoiceItemRepository;
import com.project.retailhub.data.repository.InvoiceRepository;
import com.project.retailhub.data.repository.ProductRepository;
import com.project.retailhub.data.repository.UserRepository;
import com.project.retailhub.exception.AppException;
import com.project.retailhub.exception.ErrorCode;
import com.project.retailhub.service.InvoiceService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InvoiceServiceImpl implements InvoiceService {
    InvoiceItemRepository invoiceItemRepository;
    InvoiceRepository invoiceRepository;
    InvoiceItemMapper invoiceItemMapper;
    ProductRepository productRepository;

    @Override
    public List<InvoiceResponseForUser> getAllListInvoiceByUserId(Long userId) {
        List<Invoice> listInvoice = invoiceRepository.findByUserId(userId);
        return mapInvoicesToResponses(listInvoice);
    }

    @Override
    public List<InvoiceResponseForUser> getPendingListInvoiceByUserId(Long userId) {
        // Ví dụ: lọc các hóa đơn ở trạng thái 'Pending'
        List<Invoice> pendingInvoices = invoiceRepository.findByUserIdAndStatus(userId, "PENDING");
        return mapInvoicesToResponses(pendingInvoices);
    }

    @Override
    public List<InvoiceResponseForUser> getPaidListInvoiceByUserId(Long userId) {
        List<Invoice> paidInvoices = invoiceRepository.findByUserIdAndStatus(userId, "PAID");
        return mapInvoicesToResponses(paidInvoices);
    }

    @Override
    public InvoiceResponseForUser createNewInvoice(InvoiceRequestCreate request) {

        Invoice invoice = invoiceRepository.save(Invoice.builder()
                .userId(request.getUserId())
                .customerId(request.getCustomerId())
                .status("PENDING")
                .invoiceDate(new Date())
                .totalAmount(BigDecimal.valueOf(0))
                .totalPayment(BigDecimal.valueOf(0))
                .totalTax(BigDecimal.valueOf(0))
                .build()
        );
        if (invoice.getInvoiceId() == null) {
            throw new RuntimeException("Khong the tao hoa don moi");
        }

        return mapInvoiceToResponse(invoice);
    }

    @Override
    public void canceledInvoice(Long invoiceId) {
        Invoice i = invoiceRepository.findById(invoiceId)
                .orElseThrow( () -> new RuntimeException("Khong tim thay hoa don"));
        i.setStatus("CANCELLED");
        invoiceRepository.save(i);
        // Xóa tất cả invoiceItem liên quan đến invoiceId
        //Làm sau
    }


    //Mapper
    private List<InvoiceResponseForUser> mapInvoicesToResponses(List<Invoice> invoices) {
        return invoices.stream()
                .map(this::mapInvoiceToResponse)
                .toList();
    }

    private InvoiceResponseForUser mapInvoiceToResponse(Invoice invoice) {
        List<InvoiceItemResponse> listItem = invoiceItemMapper
                .toListInvoiceResponse(invoiceItemRepository.findByInvoiceId(invoice.getInvoiceId()));
        // Lặp qua từng InvoiceItemResponse và bổ sung productName
        listItem.forEach(item -> {
            // Lấy productId từ InvoiceItemResponse
            Long productId = item.getProductId();

            // Tìm sản phẩm từ ProductRepository
            var product = productRepository.findById(productId)
                    .orElseThrow(() -> new AppException(ErrorCode.NO_PRODUCTS_FOUND));

            // Bổ sung productName vào InvoiceItemResponse
            item.setProductName(product.getProductName());
        });
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
}