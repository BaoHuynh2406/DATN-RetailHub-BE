package com.project.retailhub.api.PaymentAPI;

import com.project.retailhub.data.dto.request.PaymentMethodsRequest;
import com.project.retailhub.data.dto.response.ResponseObject;
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
    public ResponseObject<?> getPaymentMethodById(@PathVariable("paymentMethodId") int paymentMethodId) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(paymentMethodsService.getPaymentMethods(paymentMethodId));
        log.info("Fetched payment method with ID " + paymentMethodId);
        return resultApi;
    }

    @PostMapping("/create")
    public ResponseObject<?> createPaymentMethod(@RequestBody PaymentMethodsRequest request) {
        var resultApi = new ResponseObject<>();
        paymentMethodsService.addPaymentMethod(request);
        resultApi.setMessage("Payment method added successfully");
        log.info("Added new payment method");
        return resultApi;
    }

    @PutMapping("/update")
    public ResponseObject<?> updatePaymentMethod(@RequestBody PaymentMethodsRequest request) {
        var resultApi = new ResponseObject<>();
        paymentMethodsService.updatePaymentMethod(request);
        resultApi.setMessage("Payment method updated successfully");
        log.info("Updated payment method with ID " + request.getPaymentMethodId() + " successfully");
        return resultApi;
    }

    @DeleteMapping("/delete/{paymentMethodId}")
    public ResponseObject<?> deletePaymentMethod(@PathVariable("paymentMethodId") int paymentMethodId) {
        var resultApi = new ResponseObject<>();
        paymentMethodsService.deletePaymentMethod(paymentMethodId);
        resultApi.setMessage("Payment method deleted successfully");
        log.info("Deleted payment method with ID " + paymentMethodId);
        return resultApi;
    }
}
