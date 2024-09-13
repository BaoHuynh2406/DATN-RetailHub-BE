package com.project.retailhub.service;

import com.project.retailhub.data.dto.request.Tax.TaxRequest;
import com.project.retailhub.data.dto.response.Tax.TaxResponse;

import java.util.List;

public interface TaxService
{
    void addNewTax(TaxRequest request);

    void updateTax(TaxRequest request);

    void deleteTax(String taxId);

    TaxResponse findTaxByTaxId(String taxId);

    void restoreTax(String taxId);

    List<TaxResponse> findAllActiveTaxes();

    List<TaxResponse> findAllTaxes();

}
