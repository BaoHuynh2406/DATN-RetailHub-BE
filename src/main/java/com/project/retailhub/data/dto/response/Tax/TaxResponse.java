package com.project.retailhub.data.dto.response.Tax;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class TaxResponse {
    String taxId;
    String taxName;
    double taxRate;
}
