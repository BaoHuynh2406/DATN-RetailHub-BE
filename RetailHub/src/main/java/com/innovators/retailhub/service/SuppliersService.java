package com.innovators.retailhub.service;

import com.innovators.retailhub.data.dto.SuppliersDTO;

import java.util.List;

public interface SuppliersService
{
    List<SuppliersDTO> findAllSuppliers();
}
