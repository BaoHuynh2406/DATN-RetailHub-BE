package com.project.retailhub.service;

import com.project.retailhub.data.dto.request.product.ProductRequest;
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
}
