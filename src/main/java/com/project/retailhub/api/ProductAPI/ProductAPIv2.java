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
import org.springframework.security.access.prepost.PreAuthorize;
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

//    Lấy sản phẩm có sẵn
    @GetMapping("/getAll-available-product")
    public ResponseObject<?> doGetFindAllAvailable(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(productService.findAllProductPaginationAvailableAndDiscount(page, size));
        log.info("Get all available products");

//      Trả về danh sách sản phẩm đang còn bán (chưa xóa) với phân trang.
        return resultApi;
    }


//    Lấy sản phẩm đã xóa
    @GetMapping("/getAll-deleted-product")
    public ResponseObject<?> doGetFindAllDeleted(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(productService.findAllProductPaginationDeleted(page, size));
        log.info("Get all deleted products");

//      Trả về danh sách các sản phẩm đã bị xóa (vẫn lưu trữ trong hệ thống) với phân trang.
        return resultApi;
    }


//    Tìm sản phẩm theo từ khóa với phân trang.
    @GetMapping("/search")
    public ResponseObject<?> searchProducts(
            @RequestParam String keyword,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "50") int size) {
        var resultApi = new ResponseObject<PageResponse<ProductResponse>>();
        resultApi.setData(productService.findProductsByKeywordWithPagination(keyword, page, size));
        resultApi.setMessage("Products retrieved successfully");
        log.info("Search products with keyword: {}", keyword);
        return resultApi;
    }
}
