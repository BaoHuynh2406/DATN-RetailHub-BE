package com.project.retailhub.data.repository;

import com.project.retailhub.data.entity.Tax;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxRepository extends JpaRepository<Tax,String>
{
}
