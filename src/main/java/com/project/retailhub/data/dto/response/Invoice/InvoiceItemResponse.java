package com.project.retailhub.data.dto.response.Invoice;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class InvoiceItemResponse {
     Long invoiceItemId;
     String productName;
     Integer quantity;
     BigDecimal unitPrice;
     BigDecimal taxAmount;
     Long productId;
}
