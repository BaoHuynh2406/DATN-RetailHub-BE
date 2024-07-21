package com.innovators.retailhub.data.repository;

import com.innovators.retailhub.data.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Products, Integer> {
}
