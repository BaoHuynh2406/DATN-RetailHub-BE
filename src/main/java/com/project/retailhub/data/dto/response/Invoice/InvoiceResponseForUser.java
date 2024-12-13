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
public class InvoiceResponseForUser {
    Long invoiceId;
    Long customerId;
    String customerName;
    Long userId;
    String userFullName;
    Date invoiceDate;
    BigDecimal totalTax;
    BigDecimal totalAmount;
    BigDecimal totalPayment;
    BigDecimal discountAmount;
    BigDecimal finalTotal;
    String status;
    List<InvoiceItemResponse> listItem;
}
