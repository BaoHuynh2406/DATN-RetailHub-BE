package com.project.retailhub.data.mapper;

import com.project.retailhub.data.dto.request.PaymentMethodsRequest;
import com.project.retailhub.data.dto.response.PaymentMethodsResponse;
import com.project.retailhub.data.entity.PaymentMethod;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMethodsMapper {
    //Chuyển từ request sang entity
    PaymentMethod toPaymentMethod(PaymentMethodsRequest request);

    //Chuyển từ entity sang response
    PaymentMethodsResponse toPaymentMethodsResponse(PaymentMethod paymentMethod);

    List<PaymentMethodsResponse> toPaymentMethodsResponseList(List<PaymentMethod> paymentMethods);

    List<PaymentMethod> toPaymentMethodsList(List<PaymentMethodsRequest> requests);
}
