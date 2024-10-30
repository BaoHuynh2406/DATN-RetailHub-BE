package com.project.retailhub.data.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class PaymentMethodsResponse {
    int paymentMethodId;
    String paymentName;
    String image;
}
