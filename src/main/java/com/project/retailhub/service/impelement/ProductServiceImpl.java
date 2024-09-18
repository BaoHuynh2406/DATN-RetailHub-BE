package com.project.retailhub.service.impelement;

import com.project.retailhub.data.dto.request.product.ProductRequest;
import com.project.retailhub.data.dto.response.product.ProductResponse;
import com.project.retailhub.data.entity.Category;
import com.project.retailhub.data.entity.Product;
import com.project.retailhub.data.entity.Tax;
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
        if (productRepository.existsByBarcode(request.getBarcode())) {
            throw new AppException(ErrorCode.PRODUCT_ALREADY_EXISTS);
        }

        // Chuyển đổi ID danh mục và thuế thành đối tượng với truyền @Context
        Product product = productMapper.toProduct(request, categoryRepository, taxRepository);
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

        // Chuyển đổi ID danh mục và thuế thành đối tượng với truyền @Context
        Product updatedProduct = productMapper.toProduct(request, categoryRepository, taxRepository);

        // Cập nhật các thông tin cần thiết
        product.setBarcode(updatedProduct.getBarcode());
        product.setCategory(updatedProduct.getCategory());
        product.setTax(updatedProduct.getTax());
        // Cập nhật các trường khác nếu cần

        // Lưu thông tin khi cập nhật thành công
        productRepository.save(product);
    }




    @Override
    public ProductResponse getProduct(long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        ProductResponse response = productMapper.toProductResponse(product);
        log.info("ProductResponse: {}", response);
        return response;
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

    @Override
    public List<ProductResponse> getAllByCategoryId(int categoryId) {
        // Tìm danh mục dựa trên categoryId
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        // Tìm tất cả sản phẩm thuộc danh mục
        List<Product> products = productRepository.findAllByCategory(category);

        // Kiểm tra xem danh sách sản phẩm có rỗng không
        if (products.isEmpty()) {
            throw new AppException(ErrorCode.NO_PRODUCTS_FOUND);
        }

        // Chuyển đổi danh sách sản phẩm thành danh sách ProductResponse và trả về
        return productMapper.toProductResponseList(products);
    }
}
