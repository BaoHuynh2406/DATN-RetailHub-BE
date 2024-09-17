package com.project.retailhub.service;

import com.project.retailhub.data.dto.request.product.ProductRequest;
import com.project.retailhub.data.dto.response.product.ProductResponse;

import java.util.List;

public interface ProductService
{
    void appProduct(ProductRequest request);

    void updateProduct(ProductRequest request);

    ProductResponse getProduct(long productId);

    ProductResponse getMyInfo();

    void deleteProduct(long productId);

    void restoreProduct(long productId);

    void toggleActiveProduct(long productId);

    List<ProductResponse> findAllProduct();

    List<ProductResponse> findAllAvailableProduct();

    List<ProductResponse> findAllDeleteProduct();

    ProductResponse getByBarcode(String barcode);
}
