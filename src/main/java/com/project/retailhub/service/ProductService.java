package com.project.retailhub.service;

import com.project.retailhub.data.dto.request.product.ProductRequest;
import com.project.retailhub.data.dto.response.Pagination.PageResponse;
import com.project.retailhub.data.dto.response.UserResponse;
import com.project.retailhub.data.dto.response.product.ProductResponse;
import com.project.retailhub.data.entity.Product;
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

    ProductResponse findById(Long productId);

    ProductResponse getByName(String productName);

    ProductResponse getByBarcode(String barcode);

    List<ProductResponse> getAllByCategoryId(int categoryId);

    List<ProductResponse> findProductsByProductIdLike(Long productId);

    List<ProductResponse> findByProductNameLike(String productName);

    List<ProductResponse> findByProductBarcodeLike(String barcode);

    PageResponse<ProductResponse> findAllProductPagination(int page, int size);

    PageResponse<ProductResponse> findAllProductPaginationAvailable(int page, int size);

    PageResponse<ProductResponse> findAllProductPaginationDeleted(int page, int size);

    PageResponse<ProductResponse> findProductsByNameContainingWithPagination(String keyword, int page, int size);

}
