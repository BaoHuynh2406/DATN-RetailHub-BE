package com.project.retailhub.service;

import com.project.retailhub.data.dto.request.Tax.TaxRequest;
import com.project.retailhub.data.entity.Tax;

public interface TaxService
{
    void addNewTax(TaxRequest request);

    void updateTax(TaxRequest request);

    void deleteTax(String taxId);


}
