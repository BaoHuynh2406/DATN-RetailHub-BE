package com.project.retailhub.service.impelement;

import com.project.retailhub.data.dto.request.InvoiceRequest.InvoiceRequestCreate;
import com.project.retailhub.data.dto.response.Invoice.InvoiceResponseForUser;
import com.project.retailhub.data.entity.Invoice;
import com.project.retailhub.data.mapper.InvoiceItemMapper;
import com.project.retailhub.data.mapper.InvoiceMapper;
import com.project.retailhub.data.repository.InvoiceItemRepository;
import com.project.retailhub.data.repository.InvoiceRepository;
import com.project.retailhub.data.repository.ProductRepository;
import com.project.retailhub.service.InvoiceService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InvoiceServiceImpl implements InvoiceService {
    InvoiceItemRepository invoiceItemRepository;
    InvoiceRepository invoiceRepository;
    InvoiceItemMapper invoiceItemMapper;
    InvoiceMapper invoiceMapper;
    ProductRepository productRepository;

    /**
     * Retrieves a list of all invoices associated with a specific user.
     *
     * @param userId The unique identifier of the user.
     * @return A list of {@link InvoiceResponseForUser} objects representing the invoices.
     */
    @Override
    public List<InvoiceResponseForUser> getAllListInvoiceByUserId(Long userId) {
        List<Invoice> listInvoice = invoiceRepository.findByUserId(userId);
        return invoiceMapper.toInvoiceResponseForUserList(
                listInvoice,
                invoiceItemRepository,
                productRepository,
                invoiceItemMapper);
    }

    /**
     * Retrieves a list of pending invoices associated with a specific user.
     *
     * @param userId The unique identifier of the user.
     * @return A list of {@link InvoiceResponseForUser} objects representing the pending invoices.
     */
    @Override
    public List<InvoiceResponseForUser> getPendingListInvoiceByUserId(Long userId) {
        List<Invoice> pendingInvoices = invoiceRepository.findByUserIdAndStatus(userId, "PENDING");
        return invoiceMapper.toInvoiceResponseForUserList(
                pendingInvoices,
                invoiceItemRepository,
                productRepository,
                invoiceItemMapper);
    }

    /**
     * Retrieves a list of paid invoices associated with a specific user.
     *
     * @param userId The unique identifier of the user.
     * @return A list of {@link InvoiceResponseForUser} objects representing the paid invoices.
     */
    @Override
    public List<InvoiceResponseForUser> getPaidListInvoiceByUserId(Long userId) {
        List<Invoice> paidInvoices = invoiceRepository.findByUserIdAndStatus(userId, "PAID");
        return invoiceMapper.toInvoiceResponseForUserList(
                paidInvoices,
                invoiceItemRepository,
                productRepository,
                invoiceItemMapper);
    }

    /**
     * Creates a new invoice for a specific user.
     *
     * @param request The {@link InvoiceRequestCreate} object containing the necessary information for creating the invoice.
     * @return The {@link InvoiceResponseForUser} object representing the newly created invoice.
     * @throws RuntimeException If the invoice could not be created.
     */
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

        return invoiceMapper.toInvoiceResponseForUser(
                invoice,
                invoiceItemRepository,
                productRepository,
                invoiceItemMapper);
    }

    /**
     * Cancels an existing invoice.
     *
     * @param invoiceId The unique identifier of the invoice to be canceled.
     * @throws RuntimeException If the invoice could not be found.
     */
    @Override
    public void canceledInvoice(Long invoiceId) {
        Invoice i = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Khong tim thay hoa don"));
        invoiceItemRepository.deleteByInvoiceId(invoiceId);
        invoiceRepository.delete(i);
    }

    @Override
    public void increaseQuantity(Long invoiceId, Long invoiceItemId, Integer amount) {

    }

    @Override
    public void decreaseQuantity(Long invoiceId, Long invoiceItemId, Integer amount) {

    }

    @Override
    public void removeItem(Long invoiceId, Long invoiceItemId) {

    }
}