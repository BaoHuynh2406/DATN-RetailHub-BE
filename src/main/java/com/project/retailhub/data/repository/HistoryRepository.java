package com.project.retailhub.data.repository;

import com.project.retailhub.data.entity.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

    // Lấy danh sách lịch sử theo phân trang, sắp xếp từ mới đến cũ
    Page<History> findAllByOrderByTimestampDesc(Pageable pageable);

    // Lấy danh sách lịch sử của một khách hàng cụ thể, sắp xếp từ mới đến cũ
    Page<History> findByCustomer_CustomerIdOrderByTimestampDesc(Long customerId, Pageable pageable);

    // Lấy danh sách tất cả lịch sử của một khách hàng cụ thể
    List<History> findByCustomer_CustomerId(Long customerId);
}
