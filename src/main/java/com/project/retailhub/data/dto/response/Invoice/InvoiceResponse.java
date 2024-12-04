package com.project.retailhub.data.dto.response.Invoice;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class InvoiceResponse {
    Long invoiceId;
    String fullName;
    Long userId;
    Long customerId;
    String phoneNumber;
    Date invoiceDate;
    BigDecimal finalTotal;
    String status;
}
