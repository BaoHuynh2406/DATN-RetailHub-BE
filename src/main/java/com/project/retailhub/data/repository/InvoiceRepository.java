package com.project.retailhub.data.repository;

import com.project.retailhub.data.entity.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByUserId(Long userId);

    List<Invoice> findByUserIdAndStatus(Long userId, String status);

    @Query("SELECT i FROM Invoice i " +
            "WHERE i.invoiceDate BETWEEN :start AND :end " +
            "AND (:statuses IS NULL OR i.status IN :statuses)")
    Page<Invoice> findInvoicesBetweenDatesAndStatuses(
            @Param("start") Date start,
            @Param("end") Date end,
            @Param("statuses") List<String> statuses,
            Pageable pageable
    );

    @Query("SELECT i FROM Invoice i " +
            "WHERE (:statuses IS NULL OR i.status IN :statuses) AND (i.userId = :userId)")
    Page<Invoice> findInvoicesForUserAndStatus(
            @Param("userId") Long userId,
            @Param("statuses") List<String> statuses,
            Pageable pageable
    );

    @Query("SELECT i FROM Invoice i " +
            "WHERE i.invoiceDate BETWEEN :start AND :end " +
            "AND (:statuses IS NULL OR i.status IN :statuses) " +
            "ORDER BY i.invoiceDate ASC")
    List<Invoice> findInvoicesBetweenDatesAndStatuses(
            @Param("start") Date start,
            @Param("end") Date end,
            @Param("statuses") List<String> statuses
    );
    // Đếm số lượng hóa đơn theo ngày từ 00:00 đến 23:59 và trạng thái PAID
    @Query("SELECT COUNT(i) FROM Invoice i " +
                "WHERE i.invoiceDate >= :startOfDay " +
                "AND i.invoiceDate <= :endOfDay " +
                "AND i.status = 'PAID'")
        Long countInvoicesByDateAndStatus(@Param("startOfDay") Date startOfDay, @Param("endOfDay") Date endOfDay);
    // Tính tổng doanh thu (finalTotal) cho ngày cụ thể và trạng thái PAID
        @Query("SELECT SUM(i.finalTotal) FROM Invoice i " +
                "WHERE i.invoiceDate >= :startOfDay " +
                "AND i.invoiceDate <= :endOfDay " +
                "AND i.status = 'PAID'")
        BigDecimal sumRevenueByDateAndStatus(@Param("startOfDay") Date startOfDay, @Param("endOfDay") Date endOfDay);
    // Tính tổng doanh thu (finalTotal) cho tháng và trạng thái PAID
    @Query("SELECT SUM(i.finalTotal) FROM Invoice i " +
            "WHERE i.invoiceDate >= :startOfMonth " +
            "AND i.invoiceDate <= :endOfMonth " +
            "AND i.status = 'PAID'")
    BigDecimal sumRevenueByMonthAndStatus(@Param("startOfMonth") Date startOfMonth, @Param("endOfMonth") Date endOfMonth);

    @Query(value = "SELECT MONTH(i.invoice_date) AS month, " +
            "SUM(i.final_total) AS totalRevenue, " +
            "SUM(i.final_total - i.total_cost - i.total_tax - i.discount_amount) AS totalProfit " +
            "FROM invoices i " +
            "WHERE i.status = 'PAID' AND YEAR(i.invoice_date) = :year " +
            "GROUP BY MONTH(i.invoice_date) " +
            "ORDER BY month ASC",
            nativeQuery = true)
    List<Object[]> findRevenueAndProfitByMonth(@Param("year") int year);


}


