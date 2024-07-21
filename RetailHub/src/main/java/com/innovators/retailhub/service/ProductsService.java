package com.innovators.retailhub.service;

import com.innovators.retailhub.data.dto.ProductsDTO;

import java.util.List;

public interface ProductsService
{
    List<ProductsDTO> findAllProducts();
}
