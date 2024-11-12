package com.project.retailhub.service;

import com.project.retailhub.data.dto.request.Payment.HandlePaymentRequest;
import com.project.retailhub.data.dto.request.Payment.PaymentRequest;
import com.project.retailhub.data.entity.Payment;

public interface PaymentService {
    void handlePaymentCash(HandlePaymentRequest request);

}
