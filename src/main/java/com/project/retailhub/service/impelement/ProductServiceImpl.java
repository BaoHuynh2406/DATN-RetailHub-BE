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
    public void addProduct(ProductRequest request) {
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
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        // Cập nhật thông tin sản phẩm
        product.setBarcode(request.getBarcode());

        // Lưu thông tin khi cập nhật thành công
        productRepository.save(product);
    }

    @Override
    public ProductResponse getProduct(long productId) {
        return productMapper.toProductResponse(
                productRepository.findById(productId)
                        .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND))
        );
    }

    @Override
    public void deleteProduct(long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        product.setIsDelete(true);
        productRepository.save(product);
    }

    @Override
    public void restoreProduct(long productId) {
        Product product = productRepository.findById(productId)
               .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        product.setIsDelete(false);
        productRepository.save(product);
    }

    @Override
    public List<ProductResponse> findAllProduct() {
        return productMapper.toProductResponseList(productRepository.findAll());
    }

    @Override
    public List<ProductResponse> findAllAvailableProduct() {
        return productMapper.toProductResponseList(productRepository.findAllByIsDeleteFalse());
    }

    @Override
    public List<ProductResponse> findAllDeletedProduct() {
        return productMapper.toProductResponseList(productRepository.findAllByIsDeleteTrue());
    }

    @Override
    public ProductResponse getByBarcode(String barcode) {
        Product product = productRepository.findByBarcode(barcode)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        return productMapper.toProductResponse(product);
    }
}
