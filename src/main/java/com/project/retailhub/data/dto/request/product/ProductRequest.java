package com.project.retailhub.data.dto.request.product;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class ProductRequest {
    long productId;
    String barcode;
    String productName;
    String productDescription;
    String image;
    BigDecimal cost;
    BigDecimal price;
    BigDecimal inventoryCount;
    String unit;
    String location;
    Date expiryDate;
    Boolean isActive;
    Boolean isDelete;
    int categoryId;
    String taxId;
}
