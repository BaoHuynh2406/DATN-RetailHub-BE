package com.innovators.retailhub.data.dto;

import com.innovators.retailhub.data.entity.Products;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@Data
@Builder
public class ProductsDTO
{
    private int productId;
    private String barcode;
    private String productName;
    private String productDescription;
    private String image;
    private BigDecimal cost;
    private BigDecimal quantity;
    private String unit;
    private String location;
    private Date expiryDate;
    private Boolean status;
    private String roleId;
    private String taxId;
    private int categoryId;

    public static ProductsDTO convertToDTO(Products products)
    {
        if (Objects.isNull(products)) {
            return null;
        }
        return ProductsDTO.builder()
                .productId(products.getProductId())
                .barcode(products.getBarcode())
                .productName(products.getProductName())
                .productDescription(products.getProductDescription())
                .image(products.getImage())
                .cost(products.getCost())
                .quantity(products.getQuantity())
                .unit(products.getUnit())
                .location(products.getLocation())
                .expiryDate(products.getExpiryDate())
                .status(products.getStatus())
                .taxId(products.getTax() != null ? products.getTax().getTaxId().toString() : null)
                .categoryId(products.getCategory() != null ? products.getCategory().getCategoryId() : null)
                .build();
    }
    public static List<ProductsDTO> convertToDTO(List<Products> products)
    {
        if (Objects.isNull(products) || products.isEmpty()) {
            return List.of();
        }
        return products.stream()
                .map(ProductsDTO::convertToDTO)
                .collect(toList());
    }
}
