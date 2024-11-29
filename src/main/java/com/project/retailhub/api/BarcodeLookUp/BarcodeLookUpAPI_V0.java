package com.project.retailhub.api.BarcodeLookUp;

import com.project.retailhub.data.dto.response.ResponseObject;
import com.project.retailhub.service.BarcodeLookUpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/barcode-loook-up")
public class BarcodeLookUpAPI_V0 {

    final BarcodeLookUpService barcodeLookUpService;

    @GetMapping("/c/{barcode}")
    public ResponseObject<?> doGetCustomer(@PathVariable("barcode") String barcode) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(barcodeLookUpService.getInfoProduct(barcode));
        log.info("GetBarcode: " + barcode);
        return resultApi;
    }
}
