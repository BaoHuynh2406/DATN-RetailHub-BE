package com.project.retailhub.data.repository;

import com.project.retailhub.data.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Products, Integer> {
}
