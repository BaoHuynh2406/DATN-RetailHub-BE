package com.project.retailhub.data.mapper;

import com.project.retailhub.data.dto.request.Payment.HandlePaymentRequest;

import com.project.retailhub.data.dto.response.Payment.PaymentResponse;
import com.project.retailhub.data.entity.Payment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    //Chuyển từ HandlePaymentRequest sang Entity
    Payment toPayment(HandlePaymentRequest request);



    //Chuyển từ entity sang response
    PaymentResponse toPaymentResponse(Payment payment);

    List<PaymentResponse> toPaymentResponseList(List<Payment> payment);

}
