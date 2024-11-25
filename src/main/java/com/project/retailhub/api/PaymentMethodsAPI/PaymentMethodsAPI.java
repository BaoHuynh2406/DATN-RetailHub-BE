package com.project.retailhub.api.PaymentMethodsAPI;

import com.project.retailhub.data.dto.response.ResponseObject;
import com.project.retailhub.data.entity.PaymentMethod;
import com.project.retailhub.service.PaymentMethodsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/paymentmethods")
public class PaymentMethodsAPI {
    final PaymentMethodsService paymentMethodsService;

    @GetMapping("/getAllPaymentMethods")
    public ResponseObject<?> getAllPaymentMethods() {
        var resultApi = new ResponseObject<>();
        resultApi.setData(paymentMethodsService.fillAllPaymentMethods());
        log.info("Fetched all active payment methods");
        return resultApi;
    }

    @GetMapping("/paymentMethod/{paymentMethodId}")
    public ResponseObject<?> getPaymentMethodById(@PathVariable("paymentMethodId") String paymentMethodId) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(paymentMethodsService.getPaymentMethods(paymentMethodId));
        log.info("Fetched payment method with ID " + paymentMethodId);
        return resultApi;
    }


}
