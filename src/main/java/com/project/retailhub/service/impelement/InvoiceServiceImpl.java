package com.project.retailhub.service.impelement;

import com.project.retailhub.data.dto.request.InvoiceRequest.InvoiceItemRequest;
import com.project.retailhub.data.dto.request.InvoiceRequest.InvoiceRequestCreate;
import com.project.retailhub.data.dto.response.Invoice.InvoiceChartDataResponse;
import com.project.retailhub.data.dto.response.Invoice.InvoiceItemResponse;
import com.project.retailhub.data.dto.response.Invoice.InvoiceResponse;
import com.project.retailhub.data.dto.response.Invoice.InvoiceResponseForUser;
import com.project.retailhub.data.dto.response.Pagination.PageResponse;
import com.project.retailhub.data.dto.response.UserResponse;
import com.project.retailhub.data.entity.Invoice;
import com.project.retailhub.data.entity.InvoiceItem;
import com.project.retailhub.data.entity.Product;
import com.project.retailhub.data.entity.Tax;
import com.project.retailhub.data.mapper.InvoiceItemMapper;
import com.project.retailhub.data.mapper.InvoiceMapper;
import com.project.retailhub.data.repository.InvoiceItemRepository;
import com.project.retailhub.data.repository.InvoiceRepository;
import com.project.retailhub.data.repository.ProductRepository;
import com.project.retailhub.data.repository.TaxRepository;
import com.project.retailhub.exception.AppException;
import com.project.retailhub.exception.ErrorCode;
import com.project.retailhub.service.InvoiceService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InvoiceServiceImpl implements InvoiceService {
    InvoiceItemRepository invoiceItemRepository;
    InvoiceRepository invoiceRepository;
    InvoiceItemMapper invoiceItemMapper;
    InvoiceMapper invoiceMapper;
    ProductRepository productRepository;
    TaxRepository taxRepository;

    @Override
    public InvoiceResponseForUser getInvoiceById(Long invoiceId) {
        return invoiceMapper.toInvoiceResponseForUser(invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND)), invoiceItemRepository, productRepository, invoiceItemMapper);
    }

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

    @Override
    public PageResponse<InvoiceResponse> getInvoices(Date start, Date end, String status, String sort, int page, int size) {
        // Chuẩn hóa ngày bắt đầu và kết thúc
        start = normalizeStartDate(start);
        end = normalizeEndDate(end);

        if (end.before(start)) {
            throw new RuntimeException("Ngay ket thuc phai sau ngay bat dau");
        }
        // Phân tách trạng thái thành danh sách
        List<String> statusList = parseStatusList(status);

        // Xác định sắp xếp
        Sort.Direction direction = "asc".equalsIgnoreCase(sort) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(direction, "invoiceDate"));

        // Truy vấn dữ liệu
        Page<Invoice> invoicePage = invoiceRepository.findInvoicesBetweenDatesAndStatuses(start, end, statusList, pageable);

        // Trả về phản hồi
        return PageResponse.<InvoiceResponse>builder()
                .totalPages(invoicePage.getTotalPages())
                .pageSize(invoicePage.getSize())
                .currentPage(page)
                .totalElements(invoicePage.getTotalElements())
                .data(invoiceMapper.toInvoiceResponseList(invoicePage.getContent()))
                .build();
    }

    @Override
    public List<InvoiceChartDataResponse> getInvoiceChartData(Date start, Date end, String status) {
        // Chuẩn hóa ngày bắt đầu và kết thúc
        start = normalizeStartDate(start);
        end = normalizeEndDate(end);

        if (end.before(start)) {
            throw new RuntimeException("Ngay ket thuc phai sau ngay bat dau");
        }
        // Phân tách trạng thái thành danh sách
        List<String> statusList = parseStatusList(status);

        // Truy vấn dữ liệu và ánh xạ kết quả
        return invoiceMapper.toInvoiceChartDataResponseList(
                invoiceRepository.findInvoicesBetweenDatesAndStatuses(start, end, statusList)
        );
    }

    /**
     * Chuẩn hóa ngày bắt đầu (nếu null, gán giá trị là ngày hiện tại).
     */
    private Date normalizeStartDate(Date start) {
        return start != null ? start : new Date();
    }

    /**
     * Chuẩn hóa ngày kết thúc, đặt giờ phút giây tối đa trong ngày.
     */
    private Date normalizeEndDate(Date end) {
        if (end == null) end = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(end);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * Phân tách trạng thái từ chuỗi thành danh sách.
     */
    private List<String> parseStatusList(String status) {
        return (status != null && !status.isEmpty())
                ? Arrays.asList(status.split(","))
                : null;
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
                .discountAmount(BigDecimal.valueOf(0))
                .finalTotal(BigDecimal.valueOf(0))
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
    @Transactional
    public void canceledInvoice(Long invoiceId) {
        Invoice i = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Khong tim thay hoa don"));
        invoiceItemRepository.deleteByInvoiceId(invoiceId);
        invoiceRepository.delete(i);
    }

    @Override
    public void updateQuantity(Long invoiceId, Long invoiceItemId, Integer quantity) {
        var invoiceItem = invoiceItemRepository.findById(invoiceItemId)
                .orElseThrow(() -> new RuntimeException("Invoice item not found"));

        if (quantity <= 0) {
            invoiceItemRepository.delete(invoiceItem); // Nếu quantity <= 0 thì xóa sản phẩm
        } else {
            invoiceItem.setQuantity(quantity); // Cập nhật số lượng mới
            invoiceItemRepository.save(invoiceItem); // Lưu thay đổi
        }
        handleRecalculate(invoiceId);
    }

    @Override
    public void removeItem(Long invoiceId, Long invoiceItemId) {
        invoiceItemRepository.deleteById(invoiceItemId); // Xóa sản phẩm khỏi hóa đơn
        handleRecalculate(invoiceId);
    }

    @Override
    public InvoiceItemResponse createNewInvoiceItem(InvoiceItemRequest createRequest) {
        Invoice invoice = invoiceRepository.findById(createRequest.getInvoiceId())
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        //Hóa đơn phải đang ở pending
        if (!Objects.equals(invoice.getStatus(), "PENDING")) {
            throw new AppException(ErrorCode.ORDER_ALREADY_CANCELED);
        }

        Product product = productRepository.findById(createRequest.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        if (!product.getIsActive() || product.getIsDelete()) {
            throw new AppException(ErrorCode.PRODUCT_NOT_AVAILABLE);
        }

        List<InvoiceItem> ls = invoiceItemRepository.findByInvoiceIdAndProductId(createRequest.getInvoiceId(), createRequest.getProductId());

        //Kiem tra xem san pham da co trong hoa don chua
        if (!ls.isEmpty()) {
            ls.getFirst().setQuantity(ls.getFirst().getQuantity() + 1);
            updateQuantity(createRequest.getInvoiceId(), ls.getFirst().getInvoiceItemId(), ls.getFirst().getQuantity());
            return invoiceItemMapper.toInvoiceItemResponse(ls.getFirst(), productRepository);
        }
        //Tinh tien cho san pham

        Tax tax = taxRepository.findById(product.getTaxId())
                .orElseThrow(() -> new AppException(ErrorCode.TAX_NOT_FOUND));


        var invoiceItem = invoiceItemRepository.save(InvoiceItem
                .builder()
                .invoiceId(createRequest.getInvoiceId())
                .productId(product.getProductId())
                .taxRate(tax.getTaxRate())
                .unitPrice(product.getPrice())
                .quantity(1)
                .build()
        );

        //Tinh toan lai hoa don co id
        handleRecalculate(createRequest.getInvoiceId());

        return invoiceItemMapper.toInvoiceItemResponse(invoiceItem, productRepository);
    }

    // Hàm tính toán lại hóa đơn
    public InvoiceResponseForUser handleRecalculate(Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        if (!Objects.equals(invoice.getStatus(), "PENDING")) {
            throw new AppException(ErrorCode.ORDER_ALREADY_CANCELED);
        }
        List<InvoiceItem> listInvoiceItem = invoiceItemRepository.findByInvoiceId(invoiceId);

        BigDecimal TotalTax = BigDecimal.valueOf(0);
        BigDecimal TotalAmount = BigDecimal.valueOf(0);

        for (InvoiceItem invoiceItem : listInvoiceItem) {
            // Tính tax sử dụng BigDecimal
            BigDecimal tax = BigDecimal.valueOf(invoiceItem.getQuantity())
                    .multiply(invoiceItem.getUnitPrice())
                    .multiply(BigDecimal.valueOf(invoiceItem.getTaxRate()));

            // Tính amount sử dụng BigDecimal
            BigDecimal amount = BigDecimal.valueOf(invoiceItem.getQuantity())
                    .multiply(invoiceItem.getUnitPrice());

            // Cộng giá trị tax và amount vào tổng
            TotalTax = TotalTax.add(tax);
            TotalAmount = TotalAmount.add(amount);
        }
        invoice.setTotalAmount(TotalAmount);
        invoice.setTotalTax(TotalTax);
        invoice.setFinalTotal(
                invoice.getTotalAmount()
                        .add(invoice.getTotalTax())
                        .subtract(invoice.getDiscountAmount())
        );

        if (invoice.getTotalPayment().compareTo(invoice.getFinalTotal()) >= 0
                && invoice.getTotalPayment().compareTo(BigDecimal.valueOf(0)) != 0) {
            invoice.setStatus("PAID");
        }
        invoiceRepository.save(invoice);
        return getInvoiceById(invoiceId);
    }

}