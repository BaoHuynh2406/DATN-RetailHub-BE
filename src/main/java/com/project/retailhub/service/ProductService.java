package com.project.retailhub.service;

import com.project.retailhub.data.dto.request.product.ProductRequest;
import com.project.retailhub.data.dto.response.Pagination.PageResponse;
import com.project.retailhub.data.dto.response.UserResponse;
import com.project.retailhub.data.dto.response.product.ProductResponse;
import com.project.retailhub.data.repository.CategoryRepository;

import java.util.List;

public interface ProductService
{
    void addProduct(ProductRequest request);

    void updateProduct(ProductRequest request);

    ProductResponse getProduct(long productId);

    void deleteProduct(long productId);

    void restoreProduct(long productId);

    List<ProductResponse> findAllProduct();

    List<ProductResponse> findAllAvailableProduct();

    List<ProductResponse> findAllDeletedProduct();

    ProductResponse getByBarcode(String barcode);

    List<ProductResponse> getAllByCategoryId(int categoryId);

//  Phân trang findAll Product
    PageResponse<ProductResponse> findAllProductPagination(int page, int size);

//  Phân trang theo trạng thái sản phẩm chưa bị xóa
    PageResponse<ProductResponse> findAllProductPaginationAvailable(int page, int size);

//  Phân trang theo sản phẩm ở trạng thái đã xóa
    PageResponse<ProductResponse> findAllProductPaginationDeleted(int page, int size);
}
