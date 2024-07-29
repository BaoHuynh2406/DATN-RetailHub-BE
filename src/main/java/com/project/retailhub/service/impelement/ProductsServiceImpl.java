package com.project.retailhub.service.impelement;

import com.project.retailhub.data.dto.ProductsDTO;
import com.project.retailhub.data.repository.ProductsRepository;
import com.project.retailhub.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsServiceImpl implements ProductsService
{
    @Autowired
    ProductsRepository productsRepository;

    @Override
    public List<ProductsDTO> findAllProducts()
    {
        return ProductsDTO.convertToDTO(productsRepository.findAll());
    }
}
