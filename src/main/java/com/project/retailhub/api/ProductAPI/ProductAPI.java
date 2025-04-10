package com.project.retailhub.api.ProductAPI;

import com.project.retailhub.data.dto.request.product.ProductRequest;
import com.project.retailhub.data.dto.response.ResponseObject;
import com.project.retailhub.data.dto.response.product.ProductResponse;
import com.project.retailhub.exception.AppException;
import com.project.retailhub.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductAPI {
    final ProductService productService;

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CS')")
    @GetMapping("/getAllProducts")
    public ResponseObject<List<ProductResponse>> doGetFindAll() {
        var resultApi = new ResponseObject<List<ProductResponse>>();
        resultApi.setData(productService.findAllProduct());
        log.info("Get all products");
        return resultApi;
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CS')")
    @GetMapping("/getAll-available-product")
    public ResponseObject<List<ProductResponse>> doGetFindAllAvailable() {
        var resultApi = new ResponseObject<List<ProductResponse>>();
        resultApi.setData(productService.findAllAvailableProduct());
        log.info("Get all available products");
        return resultApi;
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SC')")
    @GetMapping("/getAll-deleted-product")
    public ResponseObject<List<ProductResponse>> doGetFindAllDeleted() {
        var resultApi = new ResponseObject<List<ProductResponse>>();
        resultApi.setData(productService.findAllDeletedProduct());
        log.info("Get all deleted products");
        return resultApi;
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SC')")
    @GetMapping("/{productId}")
    public ResponseObject<ProductResponse> doGetProduct(@PathVariable("productId") long productId) {
        var resultApi = new ResponseObject<ProductResponse>();
        resultApi.setData(productService.getProduct(productId));
        log.info("Get product with ID " + productId);
        return resultApi;
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SC')")
    @GetMapping("/searchByProductIdLike")
    public List<ProductResponse> searchProductByIdLike(@RequestParam Long productId) {
        return productService.findProductsByProductIdLike(productId);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SC')")
    @GetMapping("/searchByProductNameLike")
    public List<ProductResponse> searchProductByNameContaining(@RequestParam String productName) {
        return productService.findByProductNameLike(productName);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SC')")
    @GetMapping("/searchByProductBarcodeLike")
    public List<ProductResponse> searchProductByBarcodeContaining(@RequestParam String barcode) {
        return productService.findByProductBarcodeLike(barcode);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SC')")
    @GetMapping("/category/{categoryId}")
    public ResponseObject<List<ProductResponse>> getAllByCategoryId(@PathVariable int categoryId) {
        var resultApi = new ResponseObject<List<ProductResponse>>();
        try {
            resultApi.setData(productService.getAllByCategoryId(categoryId));
            resultApi.setMessage("Products retrieved successfully");
        } catch (AppException e) {
            resultApi.setCode(e.getErrorCode().getCode());
            resultApi.setMessage(e.getMessage());
        }
        log.info("Get products by category ID " + categoryId);
        return resultApi;
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SC')")
    @GetMapping("/barcode/{barcode}")
    public ResponseObject<ProductResponse> doGetProductByBarcode(@PathVariable("barcode") String barcode) {
        var resultApi = new ResponseObject<ProductResponse>();
        resultApi.setData(productService.getByBarcode(barcode));
        log.info("Get product with barcode " + barcode);
        return resultApi;
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SC')")
    @GetMapping("/productName/{productName}")
    public ResponseObject<ProductResponse> doGetProductByName(@PathVariable("productName") String productName) {
        var resultApi = new ResponseObject<ProductResponse>();
        // Gọi phương thức service để tìm sản phẩm theo tên
        resultApi.setData(productService.getByName(productName));
        log.info("Get product with name: " + productName);
        return resultApi;
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SC')")
    @GetMapping("/productId/{productId}")
    public ResponseObject<ProductResponse> doGetProductById(@PathVariable("productId") Long productId) {
        var resultApi = new ResponseObject<ProductResponse>();
        resultApi.setData(productService.findById(productId));
        log.info("Get product with id: " + productId);
        return resultApi;
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SC')")
    @PostMapping("/create")
    public ResponseObject<Void> doPostCreateProduct(@RequestBody ProductRequest request) {
        var resultApi = new ResponseObject<Void>();
        productService.addProduct(request);
        resultApi.setMessage("Product added successfully");
        log.info("Added product successfully");
        return resultApi;
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SC')")
    @PutMapping("/update")
    public ResponseObject<Void> doPutUpdateProduct(@RequestBody ProductRequest request) {
        var resultApi = new ResponseObject<Void>();
        productService.updateProduct(request);
        resultApi.setMessage("Product updated successfully");
        log.info("Updated product with ID " + request.getProductId() + " successfully");
        return resultApi;
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SC')")
    @DeleteMapping("/delete/{productId}")
    public ResponseObject<Void> doDeleteProduct(@PathVariable("productId") long productId) {
        var resultApi = new ResponseObject<Void>();
        productService.deleteProduct(productId);
        resultApi.setMessage("Product deleted successfully");
        log.info("Deleted product with ID " + productId + " successfully");
        return resultApi;
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SC')")
    @PutMapping("/restore/{productId}")
    public ResponseObject<Void> doRestoreProduct(@PathVariable("productId") long productId) {
        var resultApi = new ResponseObject<Void>();
        productService.restoreProduct(productId);
        resultApi.setMessage("Product restored successfully");
        log.info("Restored product with ID " + productId + " successfully");
        return resultApi;
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SC')")
    @GetMapping("/expiring-soon")
    public ResponseObject<List<ProductResponse>> getProductsExpiringSoon() {
        var resultApi = new ResponseObject<List<ProductResponse>>();
        try {
            // Gọi phương thức trong service để lấy danh sách sản phẩm hết hạn trong 1 tháng
            resultApi.setData(productService.findProductsExpiringSoon());
            resultApi.setMessage("Retrieved products expiring within 1 month successfully");
        } catch (AppException e) {
            // Xử lý ngoại lệ nếu có lỗi xảy ra
            resultApi.setCode(e.getErrorCode().getCode());
            resultApi.setMessage(e.getMessage());
        }
        log.info("Retrieved products expiring within 1 month");
        return resultApi;
    }
}
