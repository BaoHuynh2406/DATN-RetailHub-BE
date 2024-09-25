package com.project.retailhub.data.dto.response.product;

import com.project.retailhub.data.dto.response.Category.CategoryResponse;
import com.project.retailhub.data.dto.response.Tax.TaxResponse;
import com.project.retailhub.data.entity.Category;
import com.project.retailhub.data.entity.Tax;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ProductResponse {
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
    CategoryResponse category;
    TaxResponse tax;
}
