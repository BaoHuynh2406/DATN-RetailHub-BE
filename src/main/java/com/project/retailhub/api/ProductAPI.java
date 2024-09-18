package com.project.retailhub.api;

import com.project.retailhub.data.dto.request.product.ProductRequest;
import com.project.retailhub.data.dto.response.ResponseObject;
import com.project.retailhub.data.dto.response.product.ProductResponse;
import com.project.retailhub.exception.AppException;
import com.project.retailhub.exception.ErrorCode;
import com.project.retailhub.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductAPI {
    final ProductService productService;

    @GetMapping("/getAllProducts")
    public ResponseObject<List<ProductResponse>> doGetFindAll() {
        var resultApi = new ResponseObject<List<ProductResponse>>();
        resultApi.setData(productService.findAllProduct());
        log.info("Get all products");
        return resultApi;
    }

    @GetMapping("/getAll-available-product")
    public ResponseObject<List<ProductResponse>> doGetFindAllAvailable() {
        var resultApi = new ResponseObject<List<ProductResponse>>();
        resultApi.setData(productService.findAllAvailableProduct());
        log.info("Get all available products");
        return resultApi;
    }

    @GetMapping("/getAll-deleted-product")
    public ResponseObject<List<ProductResponse>> doGetFindAllDeleted() {
        var resultApi = new ResponseObject<List<ProductResponse>>();
        resultApi.setData(productService.findAllDeletedProduct());
        log.info("Get all deleted products");
        return resultApi;
    }

    @GetMapping("/{productId}")
    public ResponseObject<ProductResponse> doGetProduct(@PathVariable("productId") long productId) {
        var resultApi = new ResponseObject<ProductResponse>();
        resultApi.setData(productService.getProduct(productId));
        log.info("Get product with ID " + productId);
        return resultApi;
    }

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

    @GetMapping("/barcode/{barcode}")
    public ResponseObject<ProductResponse> doGetProductByBarcode(@PathVariable("barcode") String barcode) {
        var resultApi = new ResponseObject<ProductResponse>();
        resultApi.setData(productService.getByBarcode(barcode));
        log.info("Get product with barcode " + barcode);
        return resultApi;
    }

    @PostMapping("/create")
    public ResponseObject<Void> doPostCreateProduct(@RequestBody ProductRequest request) {
        var resultApi = new ResponseObject<Void>();
        productService.addProduct(request);
        resultApi.setMessage("Product added successfully");
        log.info("Added product successfully");
        return resultApi;
    }

    @PutMapping("/update")
    public ResponseObject<Void> doPutUpdateProduct(@RequestBody ProductRequest request) {
        var resultApi = new ResponseObject<Void>();
        productService.updateProduct(request);
        resultApi.setMessage("Product updated successfully");
        log.info("Updated product with ID " + request.getProductId() + " successfully");
        return resultApi;
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseObject<Void> doDeleteProduct(@PathVariable("productId") long productId) {
        var resultApi = new ResponseObject<Void>();
        productService.deleteProduct(productId);
        resultApi.setMessage("Product deleted successfully");
        log.info("Deleted product with ID " + productId + " successfully");
        return resultApi;
    }

    @PutMapping("/restore/{productId}")
    public ResponseObject<Void> doRestoreProduct(@PathVariable("productId") long productId) {
        var resultApi = new ResponseObject<Void>();
        productService.restoreProduct(productId);
        resultApi.setMessage("Product restored successfully");
        log.info("Restored product with ID " + productId + " successfully");
        return resultApi;
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ResponseObject<?>> handleAppException(AppException ex) {
        ResponseObject<?> response = new ResponseObject<>();
        response.setCode(ex.getErrorCode().getCode());
        response.setMessage(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
