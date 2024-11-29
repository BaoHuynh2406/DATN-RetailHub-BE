package com.project.retailhub.service.impelement;


import com.project.retailhub.data.entity.PaymentMethod;
import com.project.retailhub.data.repository.PaymentMethodsRepository;
import com.project.retailhub.service.PaymentMethodsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentMethodsServiceImpl implements PaymentMethodsService {

    PaymentMethodsRepository paymentRepository;

    @Override
    public void addPaymentMethod(PaymentMethod request) {
        paymentRepository.save(request);
    }

    @Override
    public void updatePaymentMethod(PaymentMethod request) {
        PaymentMethod paymentMethod = paymentRepository.findById(request.getPaymentMethodId())
                .orElseThrow(() -> new RuntimeException("Payment method not found"));
        paymentMethod.setPaymentName(request.getPaymentName());
        paymentRepository.save(paymentMethod);
    }

    @Override
    public PaymentMethod getPaymentMethods(String paymentMethodId) {
        return paymentRepository.findByPaymentMethodId(paymentMethodId)
                .orElseThrow(() -> new RuntimeException("Payment method not found"));
    }

    @Override
    public void deletePaymentMethod(String paymentMethodId) {
        PaymentMethod paymentMethod = paymentRepository.findById(paymentMethodId)
                .orElseThrow(() -> new RuntimeException("Payment method not found"));
        paymentRepository.delete(paymentMethod);
    }

    @Override
    public List<PaymentMethod> fillAllPaymentMethods() {
        return paymentRepository.findAll();
    }
}
