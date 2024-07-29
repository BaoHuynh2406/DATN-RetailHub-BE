package com.project.retailhub.data.dto;

import com.project.retailhub.data.entity.Suppliers;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@Data
@Builder
public class SuppliersDTO
{
    private int supplierId;
    private String supplierName;
    private String supplierDescription;
    private String supplierPhoneNumber;
    private String supplierEmail;
    private String supplierAddress;
    private boolean active;

    public static SuppliersDTO convertToDTO(Suppliers suppliers)
    {
        if (Objects.isNull(suppliers)) {
            return null;
        }
        return SuppliersDTO.builder()
                .supplierId(suppliers.getSupplierId())
                .supplierName(suppliers.getSupplierName())
                .supplierDescription(suppliers.getSupplierDescription())
                .supplierPhoneNumber(suppliers.getSupplierPhoneNumber())
                .supplierEmail(suppliers.getSupplierEmail())
                .supplierAddress(suppliers.getSupplierAddress())
                .active(suppliers.isActive())
                .build();
    }
    public static List<SuppliersDTO> convertToDTO(List<Suppliers> suppliers)
    {
        if (Objects.isNull(suppliers) || suppliers.isEmpty()) {
            return List.of();
        }
        return suppliers.stream()
                .map(SuppliersDTO::convertToDTO)
                .collect(toList());
    }
}
