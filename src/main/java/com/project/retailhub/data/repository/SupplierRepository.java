package com.project.retailhub.data.repository;

import com.project.retailhub.data.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Integer>
{
    Optional<Supplier> findById(int id);
}
