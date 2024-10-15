package com.project.retailhub.data.dto.request.InvoiceRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceItemRequest {
    Long invoiceId;
    Long productId;
}
