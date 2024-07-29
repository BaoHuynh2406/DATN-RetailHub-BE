package com.project.retailhub.service;

import com.project.retailhub.data.dto.SuppliersDTO;

import java.util.List;

public interface SuppliersService
{
    List<SuppliersDTO> findAllSuppliers();
}
