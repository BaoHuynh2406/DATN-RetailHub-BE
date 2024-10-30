package com.project.retailhub.data.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentMethodsRequest {
    int paymentMethodId;
    String paymentName;
    String image;
}
