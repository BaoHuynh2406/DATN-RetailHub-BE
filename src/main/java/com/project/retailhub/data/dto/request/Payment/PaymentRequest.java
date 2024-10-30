package com.project.retailhub.data.dto.request.Payment;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentRequest {
    Long paymentId;
    BigDecimal amount;
    Date paymentDate;
    Long invoiceId;
    int paymentMethodId;
}
