package com.project.retailhub.api;

import com.project.retailhub.service.ProductsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api-public/product")
public class ProductsAPI
{
//    final ProductsService productsService;
//    @GetMapping("/findAllProducts")
//    public ResponseObject<?> doGetFindAllProducts()
//    {
//        var resultApi = new ResponseObject<>();
//        try {
//            resultApi.setSuccess(true);
//            resultApi.setData(productsService.findAllProducts());
//            log.info("Find All Products Success");
//            return resultApi;
//        } catch (Exception e) {
//            log.error("Error while fetching all products", e);
//            resultApi.setSuccess(false);
//            resultApi.setMessage("Error while fetching all products");
//            return resultApi;
//        }
//    }
}
