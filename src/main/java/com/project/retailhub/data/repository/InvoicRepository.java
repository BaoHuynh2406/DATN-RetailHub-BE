package com.project.retailhub.data.repository;

import com.project.retailhub.data.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoicRepository extends JpaRepository<Invoice, Long> {

}
