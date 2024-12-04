package com.project.retailhub.data.repository;

import com.project.retailhub.data.entity.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


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
}
