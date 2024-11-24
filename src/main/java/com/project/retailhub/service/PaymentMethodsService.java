package com.project.retailhub.service;

import com.project.retailhub.data.entity.PaymentMethod;

import java.util.List;

public interface PaymentMethodsService {
    void addPaymentMethod(PaymentMethod request);
    void updatePaymentMethod(PaymentMethod request);
    PaymentMethod getPaymentMethods(String paymentMethodId);
    void deletePaymentMethod(String paymentMethodId);
    List<PaymentMethod> fillAllPaymentMethods();
}
