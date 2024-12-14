package com.project.retailhub.api.Payment;

import com.project.retailhub.data.dto.request.InvoiceRequest.InvoiceRequestCreate;
import com.project.retailhub.data.dto.request.Payment.HandlePaymentRequest;
import com.project.retailhub.data.dto.response.ResponseObject;
import com.project.retailhub.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
public class PaymentAPIv1 {
    final PaymentService paymentService;

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CS')")
    @PostMapping("/handle-payment-cash")
    public ResponseObject<?> postHandlePaymentCash(
            @RequestBody HandlePaymentRequest request
    ) {
        var resultApi = new ResponseObject<>();
        paymentService.handlePaymentCash(request);
        String message = "Invoice (ID: "+request.getInvoiceId()+") just paid "+request.getAmount()+" successfully!";
        resultApi.setData(message);
        log.info(message);
        return resultApi;
    }
}
