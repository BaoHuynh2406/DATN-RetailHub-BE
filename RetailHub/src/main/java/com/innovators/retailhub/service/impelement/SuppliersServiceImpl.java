package com.innovators.retailhub.service.impelement;

import com.innovators.retailhub.data.dto.SuppliersDTO;
import com.innovators.retailhub.data.repository.SuppliersRepository;
import com.innovators.retailhub.service.SuppliersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuppliersServiceImpl implements SuppliersService
{
    @Autowired
    SuppliersRepository suppliersRepository;

    public List<SuppliersDTO> findAllSuppliers()
    {
        return SuppliersDTO.convertToDTO(suppliersRepository.findAll());
    }
}
