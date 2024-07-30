package com.project.retailhub.api;

import com.project.retailhub.service.SuppliersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api-public/suppliers")
public class SuppliersAPI
{
//    final SuppliersService suppliersService;
//    @GetMapping("/findAllSuppliers")
//    public ResponseObject<?> doGetFindAllSuppliers()
//    {
//        var resultApi = new ResponseObject<>();
//        try {
//            resultApi.setSuccess(true);
//            resultApi.setData(suppliersService.findAllSuppliers());
//            log.info("Find All Suppliers Success");
//            return resultApi;
//        } catch (Exception e) {
//            log.error("Error while fetching all suppliers", e);
//            resultApi.setSuccess(false);
//            resultApi.setMessage("Error while fetching all suppliers");
//            return resultApi;
//        }
//    }
}
