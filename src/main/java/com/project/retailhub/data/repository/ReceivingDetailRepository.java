package com.project.retailhub.data.repository;

import com.project.retailhub.data.entity.ReceivingDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReceivingDetailRepository extends JpaRepository<ReceivingDetail, Long> {
    List<ReceivingDetail> findByReceivingDetailId(Long receivingDetailId); // Sử dụng thuộc tính thật trong entity
}
