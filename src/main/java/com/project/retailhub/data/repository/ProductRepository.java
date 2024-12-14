package com.project.retailhub.data.repository;

import com.project.retailhub.data.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>
{
    Optional<Product> findByBarcode(String barcode);
    boolean existsByBarcode(String barcode);

    List<Product> findByProductNameContainingIgnoreCase(String keyword);

    List<Product> findByProductId(Long productId);

    // Tìm tất cả sản phẩm có isDelete = false( chưa bị xóa)
    List<Product> findAllByIsDeleteFalse();

    // Tìm tất cả sản phẩm có isDelete = false( chưa bị xóa) có tham số
    Page<Product> findAllByIsDeleteFalse(Pageable pageable);

    List<Product> findAllByCategoryId(int categoryId);

    // Tìm tất cả sản phẩm có isDelete = true( đã bị xóa)
    List<Product> findAllByIsDeleteTrue();

    // Tìm tất cả sản phẩm có isDelete = true( đã bị xóa) có tham số
    Page<Product> findAllByIsDeleteTrue(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE " +
            "LOWER(p.productName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "CAST(p.productId AS string) LIKE CONCAT('%', :keyword, '%') OR " +
            "LOWER(p.barcode) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Product> findByKeyword(String keyword, Pageable pageable);

    // phương thức tìm kiếm gần đúng sản phẩm theo productId
    @Query("SELECT p FROM Product p WHERE CAST(p.productId AS string) LIKE %:productId%")
    List<Product> findByProductIdLike(@Param("productId") String productId);

    @Query("SELECT p FROM Product p WHERE p.productName LIKE %:productName%")
    List<Product> findByProductNameLike(@Param("productName") String productName);

    @Query("SELECT p FROM Product p WHERE p.barcode LIKE %:barcode%")
    List<Product> findByProductBarCodeLike(@Param("barcode") String barcode);


    @Query("SELECT p.productId, p.productName, SUM(ii.quantity) " +
            "FROM Product p " +
            "JOIN InvoiceItem ii ON p.productId = ii.productId " +
            "JOIN Invoice i ON ii.invoiceId = i.invoiceId " +
            "WHERE i.status = 'PAID' " +
            "AND i.invoiceDate BETWEEN :start AND :end " +
            "GROUP BY p.productId, p.productName")
    Page<Object[]> findProductSales(
            @Param("start") Date start,
            @Param("end") Date end,
            Pageable pageable
    );

    List<Product> findByIsDeleteFalseAndIsActiveTrueAndExpiryDateBefore(LocalDate expiryDate);
}
