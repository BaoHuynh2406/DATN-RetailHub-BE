package com.project.retailhub.data.repository;

import com.project.retailhub.data.entity.Discounts;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;


public interface DiscountsRepository extends JpaRepository<Discounts, Long> {

    Page<Discounts> findAll(Pageable pageable);

    @Query("SELECT d FROM Discounts d WHERE d.active = true AND :currentDate BETWEEN d.startDate AND d.endDate")
    List<Discounts> findActiveDiscounts(@Param("currentDate") Date currentDate);

    @Query("SELECT d FROM Discounts d WHERE d.active = true AND :currentDate BETWEEN d.startDate AND d.endDate"
    +" AND d.productId = :productId ORDER BY d.discountRate DESC")
    List<Discounts> findFirstActiveDiscountByProductId(@Param("currentDate") Date currentDate,
                                                       @Param("productId") long productId);
}
