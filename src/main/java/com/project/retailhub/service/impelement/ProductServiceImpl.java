package com.project.retailhub.service.impelement;

import com.project.retailhub.data.dto.request.product.ProductRequest;
import com.project.retailhub.data.dto.response.Pagination.PageResponse;
import com.project.retailhub.data.dto.response.UserResponse;
import com.project.retailhub.data.dto.response.product.ProductResponse;
import com.project.retailhub.data.entity.*;
import com.project.retailhub.data.mapper.ProductMapper;
import com.project.retailhub.data.repository.CategoryRepository;
import com.project.retailhub.data.repository.ProductRepository;
import com.project.retailhub.data.repository.TaxRepository;
import com.project.retailhub.exception.AppException;
import com.project.retailhub.exception.ErrorCode;
import com.project.retailhub.service.DiscountsService;
import com.project.retailhub.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductServiceImpl implements ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
    ProductRepository productRepository;
    CategoryRepository categoryRepository;
    TaxRepository taxRepository;
    ProductMapper productMapper;
    DiscountsService discountsService;

    @Override
    public void addProduct(ProductRequest request) {
        if (productRepository.existsByBarcode(request.getBarcode())) {
            throw new AppException(ErrorCode.PRODUCT_ALREADY_EXISTS);
        }

        // Chuyển đổi ID danh mục và thuế thành đối tượng với truyền @Context
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

        // Chuyển đổi ID danh mục và thuế thành đối tượng với truyền @Context
        Product updatedProduct = productMapper.toProduct(request);

        // Cập nhật các thông tin cần thiết
        product.setBarcode(request.getBarcode());
        product.setCategoryId(request.getCategoryId());
        product.setProductName(request.getProductName());
        product.setProductDescription(request.getProductDescription());
        product.setImage(request.getImage());
        product.setCost(request.getCost());
        product.setPrice(request.getPrice());
        product.setInventoryCount(request.getInventoryCount());
        product.setUnit(request.getUnit());
        product.setLocation(request.getLocation());
        product.setExpiryDate(request.getExpiryDate());
        product.setIsActive(request.getIsActive());
        product.setIsDelete(request.getIsDelete());
        product.setTaxId(request.getTaxId());
        // Cập nhật các trường khác nếu cần

        // Lưu thông tin khi cập nhật thành công
        productRepository.save(product);
    }

    @Override
    public ProductResponse getProduct(long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        ProductResponse response = productMapper.toProductResponse(product, categoryRepository, taxRepository);
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
        return productMapper.toProductResponseList(productRepository.findAll(), categoryRepository, taxRepository);
    }

    @Override
    public List<ProductResponse> findAllAvailableProduct() {
        return productMapper.toProductResponseList(productRepository.findAllByIsDeleteFalse(), categoryRepository, taxRepository);
    }

    @Override
    public List<ProductResponse> findAllDeletedProduct() {
        return productMapper.toProductResponseList(productRepository.findAllByIsDeleteTrue(), categoryRepository, taxRepository);
    }

    @Override
    public ProductResponse findById(Long productId) {
        // Tìm tất cả id gần đúng nhat
        List<Product> products = productRepository.findByProductId(productId);
        if (products.isEmpty()) {
            throw new AppException(ErrorCode.PRODUCT_ID_NULL);
        }
        Product product = products.get(0);
        return productMapper.toProductResponse(product, categoryRepository, taxRepository);
    }

    @Override
    public PageResponse<ProductResponse> findProductsByKeywordWithPagination(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size); // Chuyển đổi page thành index bắt đầu từ 0
        Page<Product> productPage = productRepository.findByKeyword(keyword, pageable);

        // Kiểm tra nếu danh sách trống
        if (productPage.isEmpty()) {
            throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        }

        // Xây dựng kết quả trả về
        return PageResponse.<ProductResponse>builder()
                .totalPages(productPage.getTotalPages())
                .pageSize(productPage.getSize())
                .currentPage(page)
                .totalElements(productPage.getTotalElements())
                .data(productPage.getContent().stream()
                        .map(product -> productMapper.toProductResponse(product, categoryRepository, taxRepository)).toList())
                .build();
    }

    @Override
    public BigDecimal getCurrentQuantity(Long productId) {
        return productRepository
                .findById(productId).orElseThrow(() -> new RuntimeException("Product Not found"))
                .getInventoryCount();
    }

    @Override
    public void subtractQuantity(Long productId, int quantity) {
        Product product = productRepository
                .findById(productId).orElseThrow(() -> new RuntimeException("Product Not found"));
        product.setInventoryCount(product.getInventoryCount().subtract(BigDecimal.valueOf(quantity)));

        //Để thành hết hàng nếu bằng 0
        if (product.getInventoryCount().compareTo(BigDecimal.valueOf(0.0)) <= 0) {
            product.setIsActive(false);
        }
        productRepository.save(product);
    }

    @Override
    public ProductResponse getByName(String productName) {
        // Tìm tất cả sản phẩm có tên gần đúng
        List<Product> products = productRepository.findByProductNameContainingIgnoreCase(productName);
        if (products.isEmpty()) {
            throw new AppException(ErrorCode.PRODUCT_NAME_NULL);
        }
        Product product = products.get(0);  // Lấy sản phẩm đầu tiên
        return productMapper.toProductResponse(product, categoryRepository, taxRepository);
    }

    @Override
    public ProductResponse getByBarcode(String barcode) {
        Product product = productRepository.findByBarcode(barcode)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        return productMapper.toProductResponse(product, categoryRepository, taxRepository);
    }

    @Override
    public List<ProductResponse> getAllByCategoryId(int categoryId) {
        // Tìm tất cả sản phẩm thuộc danh mục
        List<Product> products = productRepository.findAllByCategoryId(categoryId);

        // Kiểm tra xem danh sách sản phẩm có rỗng không
        if (products.isEmpty()) {
            throw new AppException(ErrorCode.NO_PRODUCTS_FOUND);
        }

        // Chuyển đổi danh sách sản phẩm thành danh sách ProductResponse và trả về
        return productMapper.toProductResponseList(products, categoryRepository, taxRepository);
    }

    @Override
    public List<ProductResponse> findProductsByProductIdLike(Long productId) {
        // Chuyển productId thành chuỗi
        String productIdStr = String.valueOf(productId);

        // Tìm tất cả các sản phẩm có id gần giống productId
        List<Product> products = productRepository.findByProductIdLike(productIdStr);

        // Kiểm tra xem danh sách sản phẩm có rỗng không
        if (products.isEmpty()) {
            throw new AppException(ErrorCode.PRODUCT_ID_NULL);
        }

        // Lấy sản phẩm đầu tiên trong danh sách
        Product product = products.get(0);

        // Chuyển sản phẩm đầu tiên thành ProductResponse và trả về trong danh sách
        return List.of(productMapper.toProductResponse(product, categoryRepository, taxRepository));
    }

    @Override
    public List<ProductResponse> findByProductNameLike(String productName) {
        // Tìm tất cả các sản phẩm có tên gần giống productName
        List<Product> products = productRepository.findByProductNameLike(productName);

        // Kiểm tra xem danh sách sản phẩm có rỗng không
        if (products.isEmpty()) {
            throw new AppException(ErrorCode.PRODUCT_NAME_NULL);
        }

        // Lấy sản phẩm đầu tiên trong danh sách
        Product product = products.get(0);

        // Chuyển sản phẩm đầu tiên thành ProductResponse và trả về trong danh sách
        return List.of(productMapper.toProductResponse(product, categoryRepository, taxRepository));
    }

    @Override
    public List<ProductResponse> findByProductBarcodeLike(String barcode) {
        List<Product> products = productRepository.findByProductBarCodeLike(barcode);
        if (products.isEmpty()) {
            throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        Product product = products.get(0);
        return List.of(productMapper.toProductResponse(product, categoryRepository, taxRepository));
    }

    @Override
    public PageResponse<ProductResponse> findAllProductPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Product> p = productRepository.findAll(pageable);
        return PageResponse.<ProductResponse>builder()
                .totalPages(p.getTotalPages())
                .pageSize(p.getSize())
                .currentPage(page)
                .totalElements(p.getTotalElements())
                .data(p.getContent().stream().map(product -> productMapper.toProductResponse(product, categoryRepository, taxRepository)).toList())
                .build();
    }

    @Override
    public PageResponse<ProductResponse> findAllProductPaginationAvailableAndDiscount(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Product> p = productRepository.findAllByIsDeleteFalse(pageable);

        List<ProductResponse> result = p.getContent()
                .stream()
                .map(product -> {

                    // Chuyển đổi Product sang ProductResponse
                    ProductResponse productResponse = productMapper
                            .toProductResponse(product, categoryRepository, taxRepository);

                    // Lấy Discount cho từng Product
                    Discounts discount = discountsService.getDiscountByProductIdAvailable(product.getProductId());
                    if (discount != null) {
                        double discountPercent = discount.getDiscountRate();
                        productResponse.setDiscountRate(discountPercent);
                    }

                    return productResponse;

                })
                .toList();

        return PageResponse.<ProductResponse>builder()
                .totalPages(p.getTotalPages())
                .pageSize(p.getSize())
                .currentPage(page)
                .totalElements(p.getTotalElements())
                .data(result)
                .build();
    }

    @Override
    public PageResponse<ProductResponse> findAllProductPaginationDeleted(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Product> p = productRepository.findAllByIsDeleteTrue(pageable);
        return PageResponse.<ProductResponse>builder()
                .totalPages(p.getTotalPages())
                .pageSize(p.getSize())
                .currentPage(page)
                .totalElements(p.getTotalElements())
                .data(p.getContent().stream().map(product -> productMapper.toProductResponse(product, categoryRepository, taxRepository)).toList())
                .build();
    }

    @Override
    public List<ProductResponse> findProductsExpiringSoon() {
        // Tính toán ngày hết hạn sau 1 tháng từ ngày hiện tại
        LocalDate oneMonthLater = LocalDate.now().plusMonths(1);

        // Truy vấn tất cả sản phẩm hợp lệ với trạng thái không bị xóa và còn hoạt động
        List<Product> products = productRepository
                .findByIsDeleteFalseAndIsActiveTrueAndExpiryDateBefore(oneMonthLater);

        // Kiểm tra xem danh sách sản phẩm có rỗng không
        if (products.isEmpty()) {
            throw new AppException(ErrorCode.NO_PRODUCTS_EXPIRING_SOON);
        }

        // Chuyển đổi danh sách sản phẩm thành danh sách ProductResponse và trả về
        return productMapper.toProductResponseList(products, categoryRepository, taxRepository);
    }

}
