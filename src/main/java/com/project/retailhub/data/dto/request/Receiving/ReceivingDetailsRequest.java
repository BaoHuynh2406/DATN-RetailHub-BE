package com.project.retailhub.data.dto.request.Receiving;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class ReceivingDetailsRequest {
    long receivingDetailId;
    BigDecimal quantity;
    BigDecimal receivingCost;
    String note;
    long receivingId;
    long productId;
}
