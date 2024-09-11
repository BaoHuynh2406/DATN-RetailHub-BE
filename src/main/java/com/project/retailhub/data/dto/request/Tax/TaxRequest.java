package com.project.retailhub.data.dto.request.Tax;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaxRequest {
    String taxId;
    String taxName;
    BigDecimal taxRate;
}
