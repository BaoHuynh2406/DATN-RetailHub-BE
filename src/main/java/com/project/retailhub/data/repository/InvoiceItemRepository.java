package com.project.retailhub.data.repository;

import com.project.retailhub.data.entity.InvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Long> {
    List<InvoiceItem> findByInvoiceId(Long invoiceId);

    void deleteByInvoiceId(Long invoiceId);

    List<InvoiceItem> findByInvoiceIdAndProductId(Long invoiceId, Long productId);
}
