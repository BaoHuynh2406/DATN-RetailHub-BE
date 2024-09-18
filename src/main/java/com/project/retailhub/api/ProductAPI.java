package com.project.retailhub.api;

import com.project.retailhub.data.dto.request.product.ProductRequest;
import com.project.retailhub.data.dto.response.ResponseObject;
import com.project.retailhub.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductAPI {
    final ProductService productService;

    @GetMapping("/getAllProducts")
    public ResponseObject<?> doGetFindAll() {
        var resultApi = new ResponseObject<>();
        resultApi.setData(productService.findAllProduct());
        log.info("Get all products");
        return resultApi;
    }

    @GetMapping("/getAll-available-product")
    public ResponseObject<?> doGetFindAllAvaliable() {
        var resultApi = new ResponseObject<>();
        resultApi.setData(productService.findAllAvailableProduct());
        log.info("Get ALL Available Products");
        return resultApi;
    }

    @GetMapping("/getAll-deleted-product")
    public ResponseObject<?> doGetFindAllDeleted() {
        var resultApi = new ResponseObject<>();
        resultApi.setData(productService.findAllDeletedProduct());
        log.info("Get ALL Deleted Products");
        return resultApi;
    }

    @GetMapping("/{productId}")
    public ResponseObject<?> doGetProduct(@PathVariable("productId") long productId) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(productService.getProduct(productId));
        log.info("Get product with ID " + productId);
        return resultApi;
    }

    @GetMapping("/barcode/{Barcode}")
    public ResponseObject<?> doGetProductByBarcode(@PathVariable("Barcode") String barcode) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(productService.getByBarcode(barcode));
        log.info("Get product with barcode " + barcode);
        return resultApi;
    }

    @PostMapping("/create")
    public ResponseObject<?> doPostCreateProduct(@RequestBody ProductRequest request) {
        var resultApi = new ResponseObject<>();
        productService.addProduct(request);
        resultApi.setMessage("Product added successfully");
        log.info("Added product successfully");
        return resultApi;
    }

    @PutMapping("/update")
    public ResponseObject<?> doPutUpdateProduct(@RequestBody ProductRequest request) throws InterruptedException {
        var resultApi = new ResponseObject<>();
        productService.updateProduct(request);
        resultApi.setMessage("Product updated successfully");
        log.info("Updated product with ID " + request.getProductId() + " successfully");
        return resultApi;
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseObject<?> doDeleteProduct(@PathVariable("productId") long productId) {
        var resultApi = new ResponseObject<>();
        productService.deleteProduct(productId);
        resultApi.setMessage("Product deleted successfully");
        log.info("Deleted product with ID " + productId + " successfully");
        return resultApi;
    }

    @PutMapping("/restore/{productId}")
    public ResponseObject<?> doRestoreProduct(@PathVariable("productId") long productId) {
        var resultApi = new ResponseObject<>();
        productService.restoreProduct(productId);
        resultApi.setMessage("Product restored successfully");
        log.info("Restored product with ID " + productId + " successfully");
        return resultApi;
    }
}
