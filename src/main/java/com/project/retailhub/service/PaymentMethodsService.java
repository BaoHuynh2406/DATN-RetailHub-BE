package com.project.retailhub.service;

import com.project.retailhub.data.dto.request.PaymentMethodsRequest;
import com.project.retailhub.data.dto.response.PaymentMethodsResponse;

import java.util.List;

public interface PaymentMethodsService {
    void addPaymentMethod(PaymentMethodsRequest request);
    void updatePaymentMethod(PaymentMethodsRequest request);
    PaymentMethodsResponse getPaymentMethods(int paymentMethodId);
    void deletePaymentMethod(int paymentMethodId);
    List<PaymentMethodsResponse> fillAllPaymentMethods();
}
