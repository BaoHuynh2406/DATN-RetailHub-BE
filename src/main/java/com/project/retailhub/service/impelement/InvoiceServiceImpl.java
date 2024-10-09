package com.project.retailhub.service.impelement;

import com.project.retailhub.data.dto.response.Invoice.InvoiceItemResponse;
import com.project.retailhub.data.dto.response.Invoice.InvoiceResponseForUser;
import com.project.retailhub.data.entity.Invoice;
import com.project.retailhub.data.entity.InvoiceItem;
import com.project.retailhub.data.mapper.InvoiceItemMapper;
import com.project.retailhub.data.repository.InvoiceItemRepository;
import com.project.retailhub.data.repository.InvoiceRepository;
import com.project.retailhub.data.repository.ProductRepository;
import com.project.retailhub.data.repository.UserRepository;
import com.project.retailhub.service.InvoiceService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InvoiceServiceImpl implements InvoiceService {
    InvoiceItemRepository invoiceItemRepository;
    InvoiceRepository invoiceRepository;
    InvoiceItemMapper invoiceItemMapper;

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


    //Mapper
    private List<InvoiceResponseForUser> mapInvoicesToResponses(List<Invoice> invoices) {
        return invoices.stream()
                .map(this::mapInvoiceToResponse)
                .toList();
    }

    private InvoiceResponseForUser mapInvoiceToResponse(Invoice invoice) {
        List<InvoiceItemResponse> listItem = invoiceItemMapper
                .toListInvoiceResponse(invoiceItemRepository.findByInvoiceId(invoice.getInvoiceId()));

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