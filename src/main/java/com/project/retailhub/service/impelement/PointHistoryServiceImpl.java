package com.project.retailhub.service.impelement;

import com.project.retailhub.data.dto.request.TransactionRequest;
import com.project.retailhub.data.dto.response.HistoryResponse;
import com.project.retailhub.data.dto.response.Pagination.PageResponse;
import com.project.retailhub.data.entity.Customer;
import com.project.retailhub.data.entity.Invoice;
import com.project.retailhub.data.entity.PointHistory;
import com.project.retailhub.data.entity.User;
import com.project.retailhub.data.mapper.PointHistoryMapper;
import com.project.retailhub.data.repository.CustomerRepository;
import com.project.retailhub.data.repository.InvoiceRepository;
import com.project.retailhub.data.repository.PointHistoryRepository;
import com.project.retailhub.data.repository.UserRepository;
import com.project.retailhub.service.CustomerService;
import com.project.retailhub.service.PointHistoryService;
import com.project.retailhub.service.UserService;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PointHistoryServiceImpl implements PointHistoryService {

    private final PointHistoryRepository repository;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final InvoiceRepository invoiceRepository;
    private final PointHistoryMapper mapper;

    @Override
    public PageResponse<HistoryResponse> getAllHistories(int page, int size) {
        // Tạo Pageable object
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "transactionDate"));
        Page<PointHistory> p = repository.findAll(pageable); // Lấy tất cả lịch sử từ repository
        List<HistoryResponse> historyResponseList = p.getContent().stream()
                .map(mapper::toResponse) // Ánh xạ từ History sang HistoryResponse
                .collect(Collectors.toList());

        for (HistoryResponse historyResponse : historyResponseList) {
            Customer customer = customerRepository.findById(historyResponse.getCustomerId())
                    .orElseThrow(
                            () -> new RuntimeException("customer not found")
                    );
            User user = userRepository.findById(historyResponse.getUserId())
                    .orElseThrow(
                            () -> new RuntimeException("user not found")
                    );
            historyResponse.setUserName(user.getFullName());
            historyResponse.setCustomerName(customer.getFullName());
        }

        // Trả về PageResponse
        return PageResponse.<HistoryResponse>builder()
                .totalPages(p.getTotalPages()) // Tổng số trang
                .pageSize(p.getSize()) // Kích thước trang
                .currentPage(page) // Trang hiện tại
                .totalElements(p.getTotalElements()) // Tổng số phần tử
                .data(historyResponseList) // Danh sách kết quả đã ánh xạ
                .build();
    }

    @Override
    public PageResponse<HistoryResponse> getHistoriesByCustomerId(Long customerId, int page, int size) {
        // Tạo Pageable object
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "transactionDate"));
        Page<PointHistory> p = repository.findByCustomerId(customerId, pageable); // Lọc theo customerId
        List<HistoryResponse> historyResponseList = p.getContent().stream()
                .map(mapper::toResponse) // Ánh xạ từ History sang HistoryResponse
                .collect(Collectors.toList());

        for (HistoryResponse historyResponse : historyResponseList) {
            Customer customer = customerRepository.findById(historyResponse.getCustomerId())
                    .orElseThrow(
                            () -> new RuntimeException("customer not found")
                    );
            User user = userRepository.findById(historyResponse.getUserId())
                    .orElseThrow(
                            () -> new RuntimeException("user not found")
                    );
            historyResponse.setUserName(user.getFullName());
            historyResponse.setCustomerName(customer.getFullName());
        }

        // Trả về PageResponse
        return PageResponse.<HistoryResponse>builder()
                .totalPages(p.getTotalPages()) // Tổng số trang
                .pageSize(p.getSize()) // Kích thước trang
                .currentPage(page) // Trang hiện tại
                .totalElements(p.getTotalElements()) // Tổng số phần tử
                .data(historyResponseList) // Danh sách kết quả đã ánh xạ
                .build();
    }

    @Override
    public HistoryResponse createTransaction(TransactionRequest request) {
        OffsetDateTime now = OffsetDateTime.now(); // Lấy ngày giờ hiện tại với múi giờ
        PointHistory entity = mapper.toEntity(request);


        //Không thực hiện nếu giao dịch có điểm là 0
        if (request.getPoints() == 0) {
            throw new RuntimeException("No points");
        }

        //Check khách hàng
        Customer customer = customerRepository.findById(entity.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        if (!customer.getIsActive() || customer.getIsDelete()) {
            throw new RuntimeException("Customer is not active or deleted");
        }

        if (customer.getCustomerId() == 1000) {
            return null;
        }

        //Check Nhân viên
        User user = userRepository.findById(entity.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        //Check Hóa đơn
        Invoice invoice = invoiceRepository.findById(entity.getInvoiceId())
                .orElseThrow(() -> new RuntimeException("Hoa Don not found"));


        // Check Giao Dich la tich diem hay doi diem
        if (entity.getPoints() >= 0) {
            //Tich diem
            customer.setPoints(customer.getPoints() + entity.getPoints());
        } else {
            //Đổi điểm
            if (customer.getPoints() >= Math.abs(entity.getPoints())) {
                customer.setPoints(customer.getPoints() + entity.getPoints());
                //Tru tien trong hoa don
                invoice.setDiscountAmount(
                        invoice.getDiscountAmount()
                                .add(
                                        BigDecimal.valueOf(Math.abs(entity.getPoints()
                                        ))));
                //Tính toán lại finalTotal
                invoice.setFinalTotal(invoice.getFinalTotal().subtract(invoice.getDiscountAmount()));
            } else {
                throw new RuntimeException("Not enough points");
            }
        }
        entity.setTransactionDate(now); // Gán ngày giờ với múi giờ vào
        PointHistory savedEntity = repository.save(entity);
        return mapper.toResponse(savedEntity);
    }


}
