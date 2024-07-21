package com.innovators.retailhub.data.repository;

import com.innovators.retailhub.data.dto.SuppliersDTO;
import com.innovators.retailhub.data.entity.Suppliers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SuppliersRepository extends JpaRepository<Suppliers, Integer>
{

}
