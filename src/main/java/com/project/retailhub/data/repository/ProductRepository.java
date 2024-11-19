package com.project.retailhub.data.repository;

import com.project.retailhub.data.entity.Category;
import com.project.retailhub.data.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>
{
    Optional<Product> findByBarcode(String barcode);
    boolean existsByBarcode(String barcode);

    Optional<Product> findByProductName(String productName);

    List<Product> findByProductNameContainingIgnoreCase(String keyword);

    // Tìm tất cả sản phẩm có isDelete = false( chưa bị xóa)
    List<Product> findAllByIsDeleteFalse();

    // Tìm tất cả sản phẩm có isDelete = false( chưa bị xóa) có tham số
    Page<Product> findAllByIsDeleteFalse(Pageable pageable);

    List<Product> findAllByCategoryId(int categoryId);

    // Tìm tất cả sản phẩm có isDelete = true( đã bị xóa)
    List<Product> findAllByIsDeleteTrue();

    // Tìm tất cả sản phẩm có isDelete = true( đã bị xóa) có tham số
    Page<Product> findAllByIsDeleteTrue(Pageable pageable);
}
