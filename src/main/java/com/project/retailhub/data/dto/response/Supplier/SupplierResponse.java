package com.project.retailhub.data.dto.response.Supplier;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class SupplierResponse {
    long supplierId;
    String supplierName;
    String supplierDescription;
    String supplierPhoneNumber;
    String supplierEmail;
    String supplierAddress;
    Boolean isDelete;
}
