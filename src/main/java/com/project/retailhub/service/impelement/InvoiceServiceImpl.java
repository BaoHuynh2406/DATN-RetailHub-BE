package com.project.retailhub.service.impelement;

import com.project.retailhub.data.dto.request.InvoiceRequest.ExChangePoinstRq;
import com.project.retailhub.data.dto.request.InvoiceRequest.InvoiceItemRequest;
import com.project.retailhub.data.dto.request.InvoiceRequest.InvoiceRequestCreate;
import com.project.retailhub.data.dto.request.InvoiceRequest.UpdateCustomerInvoiceRq;
import com.project.retailhub.data.dto.request.TransactionRequest;
import com.project.retailhub.data.dto.response.Invoice.InvoiceChartDataResponse;
import com.project.retailhub.data.dto.response.Invoice.InvoiceItemResponse;
import com.project.retailhub.data.dto.response.Invoice.InvoiceResponse;
import com.project.retailhub.data.dto.response.Invoice.InvoiceResponseForUser;
import com.project.retailhub.data.dto.response.Pagination.PageResponse;

import com.project.retailhub.data.entity.*;
import com.project.retailhub.data.mapper.InvoiceItemMapper;
import com.project.retailhub.data.mapper.InvoiceMapper;
import com.project.retailhub.data.repository.*;
import com.project.retailhub.exception.AppException;
import com.project.retailhub.exception.ErrorCode;
import com.project.retailhub.service.DiscountsService;
import com.project.retailhub.service.InvoiceService;
import com.project.retailhub.service.PointHistoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

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
    PointHistoryService pointHistoryService;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final DiscountsService discountsService;

    @Override
    public InvoiceResponseForUser getInvoiceById(Long invoiceId) {
        return invoiceMapper.toInvoiceResponseForUser(invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND)), invoiceItemRepository, productRepository, invoiceItemMapper, userRepository, customerRepository);
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
                invoiceItemMapper,
                userRepository,
                customerRepository);
    }


    @Override
    public List<InvoiceResponseForUser> getPendingListInvoiceByUserCurrent() {
        //Lấy userID từ token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        long userId = Long.parseLong(authentication.getName());

        List<Invoice> pendingInvoices = invoiceRepository.findByUserIdAndStatus(userId, "PENDING");
        return invoiceMapper.toInvoiceResponseForUserList(
                pendingInvoices,
                invoiceItemRepository,
                productRepository,
                invoiceItemMapper,
                userRepository,
                customerRepository);
    }

    @Override
    public PageResponse<InvoiceResponse> findInvoiceWithKeyWord(int page, int size, String keyword) {
        Pageable pageable = PageRequest.of(page - 1, size);
        // Truy vấn dữ liệu
        Page<Invoice> invoicePage = invoiceRepository.searchInvoices(keyword, pageable);
        //Mapping cấp 1
        List<InvoiceResponse> invoiceResponses = invoiceMapper.toInvoiceResponseList(invoicePage.getContent());

        //Mapping cấp 2
        for (InvoiceResponse invoiceResponse : invoiceResponses) {
            Customer customer = customerRepository.findById(invoiceResponse.getCustomerId()).orElseThrow(
                    () -> new RuntimeException("Customer not found")
            );

            User user = userRepository.findById(invoiceResponse.getUserId()).orElseThrow(
                    () -> new RuntimeException("User not found")
            );

            invoiceResponse.setFullName(customer.getFullName());
            invoiceResponse.setPhoneNumber(customer.getPhoneNumber());

            invoiceResponse.setUserFullName(user.getFullName());

        }

        // Trả về phản hồi
        return PageResponse.<InvoiceResponse>builder()
                .totalPages(invoicePage.getTotalPages())
                .pageSize(invoicePage.getSize())
                .currentPage(page)
                .totalElements(invoicePage.getTotalElements())
                .data(invoiceResponses)
                .build();
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

        //Mapping cấp 1
        List<InvoiceResponse> invoiceResponses = invoiceMapper.toInvoiceResponseList(invoicePage.getContent());

        //Mapping cấp 2
        for (InvoiceResponse invoiceResponse : invoiceResponses) {
            Customer customer = customerRepository.findById(invoiceResponse.getCustomerId()).orElseThrow(
                    () -> new RuntimeException("Customer not found")
            );

            User user = userRepository.findById(invoiceResponse.getUserId()).orElseThrow(
                    () -> new RuntimeException("User not found")
            );

            invoiceResponse.setFullName(customer.getFullName());
            invoiceResponse.setPhoneNumber(customer.getPhoneNumber());

            invoiceResponse.setUserFullName(user.getFullName());

        }

        // Trả về phản hồi
        return PageResponse.<InvoiceResponse>builder()
                .totalPages(invoicePage.getTotalPages())
                .pageSize(invoicePage.getSize())
                .currentPage(page)
                .totalElements(invoicePage.getTotalElements())
                .data(invoiceResponses)
                .build();
    }


    @Override
    public PageResponse<InvoiceResponse> getAllForUserCurrent(String status, String sort, int page, int size) {
        // Phân tách trạng thái thành danh sách
        List<String> statusList = parseStatusList(status);

        //Lấy userID từ token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        long userId = Long.parseLong(authentication.getName());


        // Xác định sắp xếp
        Sort.Direction direction = "asc".equalsIgnoreCase(sort) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(direction, "invoiceDate"));

        // Truy vấn dữ liệu
        Page<Invoice> invoicePage = invoiceRepository.findInvoicesForUserAndStatus(userId, statusList, pageable);


        //Mapping cấp 1
        List<InvoiceResponse> invoiceResponses = invoiceMapper.toInvoiceResponseList(invoicePage.getContent());

        //Mapping cấp 2
        for (InvoiceResponse invoiceResponse : invoiceResponses) {
            Customer customer = customerRepository.findById(invoiceResponse.getCustomerId()).orElseThrow(
                    () -> new RuntimeException("Customer not found")
            );

            User user = userRepository.findById(invoiceResponse.getUserId()).orElseThrow(
                    () -> new RuntimeException("User not found")
            );

            invoiceResponse.setFullName(customer.getFullName());
            invoiceResponse.setPhoneNumber(customer.getPhoneNumber());

            invoiceResponse.setUserFullName(user.getFullName());

        }
        // Trả về phản hồi
        return PageResponse.<InvoiceResponse>builder()
                .totalPages(invoicePage.getTotalPages())
                .pageSize(invoicePage.getSize())
                .currentPage(page)
                .totalElements(invoicePage.getTotalElements())
                .data(invoiceResponses)
                .build();
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
                invoiceItemMapper,
                userRepository,
                customerRepository);
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

        //Nếu sản phẩm có giảm giá
        Discounts discounts = discountsService.getDiscountByProductIdAvailable(product.getProductId());
        double discountRate = 0;
        if (discounts != null) {
            discountRate = discounts.getDiscountRate();
        }


        var invoiceItem = invoiceItemRepository.save(InvoiceItem
                .builder()
                .invoiceId(createRequest.getInvoiceId())
                .productId(product.getProductId())
                .taxRate(tax.getTaxRate())
                .unitPrice(product.getPrice())
                .cost(product.getCost())
                .discountRate(discountRate)
                .quantity(1)
                .build()
        );

        //Tinh toan lai hoa don co id
        handleRecalculate(createRequest.getInvoiceId());

        return invoiceItemMapper.toInvoiceItemResponse(invoiceItem, productRepository);
    }

    @Override
    public void updateCustomer(UpdateCustomerInvoiceRq rq) {
        Invoice invoice = invoiceRepository.findById(rq.getInvoiceId())
                .orElseThrow(() -> new RuntimeException("Not found invoice"));

        Customer customer = customerRepository.findById(rq.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        invoice.setCustomerId(rq.getCustomerId());
        invoiceRepository.save(invoice);
    }

    @Override
    public void exchangePoints(ExChangePoinstRq exchangePointsRequest) {
        Invoice invoice = invoiceRepository.findById(exchangePointsRequest.getInvoiceId())
                .orElseThrow(() -> new RuntimeException("Not found invoice"));

        Customer customer = customerRepository.findById(invoice.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        int transictionPoint = customer.getPoints();

        // Kiểm tra điểm sử dụng
        if (transictionPoint <= 0) {
            throw new RuntimeException("Không có điểm để đổi");
        }

        if (invoice.getFinalTotal().intValue() < transictionPoint) {
            transictionPoint = invoice.getFinalTotal().intValue();
            //Trường hơp hoá đơn 0 đồng
            if (transictionPoint == 0) {
                throw new RuntimeException("Hoá đơn đang ở giá trị tối thiểu!");
            }
        }
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setInvoiceId(invoice.getInvoiceId());
        transactionRequest.setCustomerId(invoice.getCustomerId());
        // Đổi lại thành âm
        transactionRequest.setPoints(-transictionPoint);
        transactionRequest.setDescription("Đổi " + transactionRequest.getPoints() + "điểm cho hóa đơn:" + invoice.getInvoiceId());
        transactionRequest.setUserId(invoice.getUserId());

        pointHistoryService.createTransaction(transactionRequest);
    }

    public InvoiceResponseForUser handleRecalculate(Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        // Kiểm tra trạng thái của hóa đơn
        if (!"PENDING".equals(invoice.getStatus())) {
            throw new AppException(ErrorCode.ORDER_ALREADY_CANCELED);
        }

        List<InvoiceItem> listInvoiceItem = invoiceItemRepository.findByInvoiceId(invoiceId);

        // Khởi tạo các giá trị tổng
        BigDecimal totalTax = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalCost = BigDecimal.ZERO;

        for (InvoiceItem invoiceItem : listInvoiceItem) {
            BigDecimal quantity = BigDecimal.valueOf(invoiceItem.getQuantity());
            BigDecimal unitPrice = invoiceItem.getUnitPrice();
            BigDecimal taxRate = BigDecimal.valueOf(invoiceItem.getTaxRate());
            BigDecimal cost = invoiceItem.getCost();


            // Tính giá trị hàng hóa
            BigDecimal amount = quantity.multiply(unitPrice);

            // Tính giảm giá
            if (invoiceItem.getDiscountRate() > 0) {
                amount = amount.multiply(BigDecimal.valueOf(1 - invoiceItem.getDiscountRate()));
            }

            // Tính thuế
            BigDecimal tax = amount.multiply(taxRate);
            // Tính chi phí
            BigDecimal itemCost = quantity.multiply(cost);

            // Cộng vào tổng
            totalTax = totalTax.add(tax);
            totalAmount = totalAmount.add(amount);
            totalCost = totalCost.add(itemCost);
        }

        // Cập nhật các giá trị của invoice
        invoice.setTotalAmount(totalAmount);
        invoice.setTotalTax(totalTax);
        invoice.setTotalCost(totalCost);
        invoice.setFinalTotal(totalAmount.add(totalTax).subtract(invoice.getDiscountAmount()));

        // Kiểm tra trạng thái thanh toán và cập nhật
        if (invoice.getTotalPayment().compareTo(invoice.getFinalTotal()) >= 0
                && invoice.getTotalPayment().compareTo(BigDecimal.ZERO) != 0) {
            invoice.setStatus("PAID");
        }

        // Lưu lại hóa đơn đã cập nhật
        invoiceRepository.save(invoice);

        return getInvoiceById(invoiceId);
    }


}