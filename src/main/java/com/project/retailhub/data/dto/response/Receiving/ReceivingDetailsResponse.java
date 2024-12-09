package com.project.retailhub.data.dto.response.Receiving;

import com.project.retailhub.data.dto.response.product.ProductResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ReceivingDetailsResponse {
    long receivingDetailId;
    BigDecimal quantity;
    BigDecimal receivingCost;
    String note;
    ReceivingResponse receiving;
    ProductResponse product;
}
