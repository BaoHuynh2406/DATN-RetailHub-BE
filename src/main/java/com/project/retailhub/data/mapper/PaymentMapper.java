package com.project.retailhub.data.mapper;

import com.project.retailhub.data.dto.request.Payment.PaymentRequest;
import com.project.retailhub.data.dto.response.Payment.PaymentResponse;
import com.project.retailhub.data.entity.Payment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    //Chuyển từ request sang entity
    Payment toPayment(PaymentRequest request);

    //Chuyển từ entity sang response
    PaymentResponse toPaymentResponse(Payment payment);

    List<PaymentResponse> toPaymentResponseList(List<Payment> payment);

    List<Payment> toPaymentList(List<PaymentRequest> paymentRequest);
}
