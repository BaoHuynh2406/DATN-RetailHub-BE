package com.project.retailhub.data.dto.response.Payment;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class PaymentResponse {
    Long paymentId;
    BigDecimal amount;
    Date paymentDate;
    Long invoiceId;
    String paymentMethodId;
}