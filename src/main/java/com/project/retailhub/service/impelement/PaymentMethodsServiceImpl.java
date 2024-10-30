package com.project.retailhub.service.impelement;

import com.project.retailhub.data.dto.request.PaymentMethodsRequest;
import com.project.retailhub.data.dto.response.PaymentMethodsResponse;
import com.project.retailhub.data.entity.PaymentMethod;
import com.project.retailhub.data.mapper.PaymentMethodsMapper;
import com.project.retailhub.data.repository.PaymentMethodsRepository;
import com.project.retailhub.service.PaymentMethodsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentMethodsServiceImpl implements PaymentMethodsService {

    private static final Logger log = LoggerFactory.getLogger(PaymentMethodsServiceImpl.class);
    PaymentMethodsRepository paymentRepository;
    PaymentMethodsMapper paymentMapper;

    @Override
    public void addPaymentMethod(PaymentMethodsRequest request) {
        if (paymentRepository.existsByPaymentMethodId(request.getPaymentMethodId())) {
            throw new RuntimeException("Payment method name already exists");
        }
        PaymentMethod paymentMethod = paymentMapper.toPaymentMethod(request);
        paymentRepository.save(paymentMethod);
    }

    @Override
    public void updatePaymentMethod(PaymentMethodsRequest request) {
        PaymentMethod paymentMethod = paymentRepository.findById(request.getPaymentMethodId())
                .orElseThrow(() -> new RuntimeException("Payment method not found"));
        paymentMethod.setPaymentName(request.getPaymentName());
        paymentRepository.save(paymentMethod);
    }

    @Override
    public PaymentMethodsResponse getPaymentMethods(int paymentMethodId) {
        PaymentMethod paymentMethod = paymentRepository.findByPaymentMethodId(paymentMethodId)
                .orElseThrow(() -> new RuntimeException("Payment method not found"));
        return paymentMapper.toPaymentMethodsResponse(paymentMethod);
    }

    @Override
    public void deletePaymentMethod(int paymentMethodId) {
        PaymentMethod paymentMethod = paymentRepository.findById(paymentMethodId)
                .orElseThrow(() -> new RuntimeException("Payment method not found"));
        paymentRepository.delete(paymentMethod);
    }

    @Override
    public List<PaymentMethodsResponse> fillAllPaymentMethods() {
        return paymentMapper.toPaymentMethodsResponseList(paymentRepository.findAll());
    }
}
