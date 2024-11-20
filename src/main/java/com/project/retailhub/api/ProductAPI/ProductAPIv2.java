package com.project.retailhub.api.ProductAPI;

import com.project.retailhub.data.dto.request.product.ProductRequest;
import com.project.retailhub.data.dto.response.Pagination.PageResponse;
import com.project.retailhub.data.dto.response.ResponseObject;
import com.project.retailhub.data.dto.response.product.ProductResponse;
import com.project.retailhub.exception.AppException;
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
@RequestMapping("/api/v2/product")
public class ProductAPIv2 {
    final ProductService productService;

    @GetMapping("/getAllProducts")
    public ResponseObject<?> doGetFindAll(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(productService.findAllProductPagination(page, size));
        log.info("Get all products");
        return resultApi;
    }

    @GetMapping("/getAll-available-product")
    public ResponseObject<?> doGetFindAllAvailable(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(productService.findAllProductPaginationAvailable(page, size));
        log.info("Get all available products");
        return resultApi;
    }

    @GetMapping("/getAll-deleted-product")
    public ResponseObject<?> doGetFindAllDeleted(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(productService.findAllProductPaginationDeleted(page, size));
        log.info("Get all deleted products");
        return resultApi;
    }

    @GetMapping("/search")
    public ResponseObject<PageResponse<ProductResponse>> searchProducts(
            @RequestParam String keyword,
            @RequestParam int page,
            @RequestParam int size) {
        var resultApi = new ResponseObject<PageResponse<ProductResponse>>();
        try {
            resultApi.setData(productService.findProductsByNameContainingWithPagination(keyword, page, size));
            resultApi.setMessage("Products retrieved successfully");
        } catch (AppException e) {
            resultApi.setCode(e.getErrorCode().getCode());
            resultApi.setMessage(e.getMessage());
        }
        log.info("Search products with keyword: {}", keyword);
        return resultApi;
    }


}
