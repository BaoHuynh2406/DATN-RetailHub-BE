package com.project.retailhub.service;

import com.project.retailhub.data.dto.ProductsDTO;

import java.util.List;

public interface ProductsService
{
    List<ProductsDTO> findAllProducts();
}
