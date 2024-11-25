package com.project.retailhub.data.repository;

import com.project.retailhub.data.entity.PointHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {

    // Lấy toàn bộ lịch sử (có phân trang)
    Page<PointHistory> findAll(Pageable pageable);

    // Lấy lịch sử theo ID khách hàng (có phân trang)
    Page<PointHistory> findByCustomerId(Long customerId, Pageable pageable);

    // Lấy lịch sử theo loại giao dịch (có phân trang)
    Page<PointHistory> findByTransactionType(String transactionType, Pageable pageable);

    // Lấy lịch sử theo khách hàng và loại giao dịch (có phân trang)
    Page<PointHistory> findByCustomerIdAndTransactionType(Long customerId, String transactionType, Pageable pageable);

    // Lấy lịch sử theo khoảng thời gian (có phân trang)
    Page<PointHistory> findByTransactionDateBetween(Date startDate, Date endDate, Pageable pageable);

    // Lấy lịch sử theo khách hàng và khoảng thời gian (có phân trang)
    Page<PointHistory> findByCustomerIdAndTransactionDateBetween(Long customerId, Date startDate, Date endDate, Pageable pageable);

    // Lấy lịch sử và sắp xếp theo ngày giao dịch mới nhất
    Page<PointHistory> findAllByOrderByTransactionDateDesc(Pageable pageable);

    // Lấy lịch sử theo khách hàng và sắp xếp theo ngày giao dịch mới nhất
    Page<PointHistory> findByCustomerIdOrderByTransactionDateDesc(Long customerId, Pageable pageable);

    // Tổng điểm đã đổi của khách hàng
    @Query("SELECT SUM(ph.points) FROM PointHistory ph WHERE ph.customerId = :customerId AND ph.points < 0")
    Integer getTotalRedeemedPointsByCustomerId(@Param("customerId") Long customerId);

    // Tổng điểm đã tích lũy của khách hàng
    @Query("SELECT SUM(ph.points) FROM PointHistory ph WHERE ph.customerId = :customerId AND ph.points > 0")
    Integer getTotalAccumulatedPointsByCustomerId(@Param("customerId") Long customerId);
}
