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
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Tìm sản phẩm theo barcode
    Optional<Product> findByBarcode(String barcode);

    // Kiểm tra sự tồn tại của sản phẩm theo barcode
    boolean existsByBarcode(String barcode);

    // Tìm sản phẩm theo tên (case-insensitive)
    List<Product> findByProductNameContainingIgnoreCase(String keyword);

    // Tìm sản phẩm theo ID
    List<Product> findByProductId(Long productId);

    // Tìm tất cả sản phẩm chưa bị xóa
    List<Product> findAllByIsDeleteFalse();

    // Tìm tất cả sản phẩm chưa bị xóa (có phân trang)
    Page<Product> findAllByIsDeleteFalse(Pageable pageable);

    // Tìm tất cả sản phẩm thuộc một danh mục
    List<Product> findAllByCategoryId(int categoryId);

    // Tìm tất cả sản phẩm đã bị xóa
    List<Product> findAllByIsDeleteTrue();

    // Tìm tất cả sản phẩm đã bị xóa (có phân trang)
    Page<Product> findAllByIsDeleteTrue(Pageable pageable);

    // Tìm kiếm sản phẩm theo từ khóa (gần đúng với productName, productId, hoặc barcode)
    @Query("SELECT p FROM Product p WHERE " +
            "LOWER(p.productName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "CAST(p.productId AS string) LIKE CONCAT('%', :keyword, '%') OR " +
            "LOWER(p.barcode) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Product> findByKeyword(String keyword, Pageable pageable);

    // Tìm kiếm gần đúng sản phẩm theo productId
    @Query("SELECT p FROM Product p WHERE CAST(p.productId AS string) LIKE %:productId%")
    List<Product> findByProductIdLike(@Param("productId") String productId);

    // Tìm kiếm gần đúng sản phẩm theo productName
    @Query("SELECT p FROM Product p WHERE p.productName LIKE %:productName%")
    List<Product> findByProductNameLike(@Param("productName") String productName);

    // Tìm kiếm gần đúng sản phẩm theo barcode
    @Query("SELECT p FROM Product p WHERE p.barcode LIKE %:barcode%")
    List<Product> findByProductBarCodeLike(@Param("barcode") String barcode);

    // Thống kê lượt bán sản phẩm trong một khoảng thời gian
    @Query("SELECT p.productId, p.productName, SUM(ii.quantity), p.image " +
            "FROM Product p " +
            "JOIN InvoiceItem ii ON p.productId = ii.productId " +
            "JOIN Invoice i ON ii.invoiceId = i.invoiceId " +
            "WHERE i.status = 'PAID' " +
            "AND i.invoiceDate BETWEEN :start AND :end " +
            "GROUP BY p.productId, p.productName, p.image " +
            "ORDER BY SUM(ii.quantity) DESC")
    Page<Object[]> findProductSales(
            @Param("start") Date start,
            @Param("end") Date end,
            Pageable pageable
    );

    // Tìm sản phẩm chưa xóa, đang hoạt động, và hết hạn
    List<Product> findByIsDeleteFalseAndIsActiveTrueAndExpiryDateBefore(LocalDate expiryDate);

    // Lấy top 5 sản phẩm có inventoryCount thấp nhất
    @Query("SELECT p FROM Product p WHERE p.isDelete = false AND p.isActive = true ORDER BY p.inventoryCount ASC")
    List<Product> findTop5LowestInventoryCount(Pageable pageable);
}
