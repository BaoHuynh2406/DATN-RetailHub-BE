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

}
