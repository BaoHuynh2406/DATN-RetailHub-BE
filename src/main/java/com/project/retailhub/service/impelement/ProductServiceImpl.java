package com.project.retailhub.service.impelement;

import com.project.retailhub.data.dto.request.product.ProductRequest;
import com.project.retailhub.data.dto.response.product.ProductResponse;
import com.project.retailhub.data.entity.Product;
import com.project.retailhub.data.mapper.ProductMapper;
import com.project.retailhub.data.repository.CategoryRepository;
import com.project.retailhub.data.repository.ProductRepository;
import com.project.retailhub.data.repository.TaxRepository;
import com.project.retailhub.exception.AppException;
import com.project.retailhub.exception.ErrorCode;
import com.project.retailhub.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductServiceImpl implements ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
    ProductRepository productRepository;
    CategoryRepository categoryRepository;
    TaxRepository taxRepository;
    ProductMapper productMapper;

    @Override
    public void appProduct(ProductRequest request) {
        if (productRepository.existsByBarcode(request.getBarcode()))
            throw new AppException(ErrorCode.PRODUCT_ALREADY_EXISTS);

        // Thực hiện chuyển đổi request thành entity
        Product product = productMapper.toProduct(request);
        productRepository.save(product);
    }

    @Override
    public void updateProduct(ProductRequest request) {
        long productId = request.getProductId();
        if (productId <= 0) {
            throw new AppException(ErrorCode.PRODUCT_ID_NULL);
        }
        // Tìm kiếm sản phẩm theo ID
        Product product = ProductRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        // Cập nhật thông tin sản phẩm
        product.setBarcode(request.getBarcode());

        // Lưu thông tin khi cập nhật thành công
        ProductRepository.save(product);
    }

    @Override
    public ProductResponse getProduct(long productId) {
        return null;
    }

    @Override
    public ProductResponse getMyInfo() {
        return null;
    }

    @Override
    public void deleteProduct(long productId) {

    }

    @Override
    public void restoreProduct(long productId) {

    }

    @Override
    public void toggleActiveProduct(long productId) {

    }

    @Override
    public List<ProductResponse> findAllProduct() {
        return List.of();
    }

    @Override
    public List<ProductResponse> findAllAvailableProduct() {
        return List.of();
    }

    @Override
    public List<ProductResponse> findAllDeleteProduct() {
        return List.of();
    }

    @Override
    public ProductResponse getByBarcode(String barcode) {
        return null;
    }
}
