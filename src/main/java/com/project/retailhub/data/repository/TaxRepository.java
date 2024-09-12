package com.project.retailhub.data.repository;

import com.project.retailhub.data.entity.Tax;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaxRepository extends JpaRepository<Tax,String>
{
    // Tìm tất cả các bản ghi có isDelete = true
    List<Tax> findAllByIsDelete(boolean isDelete);
    boolean existsByTaxName(String taxName);
}
