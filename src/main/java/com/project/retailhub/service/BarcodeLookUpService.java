package com.project.retailhub.service;

import com.project.retailhub.data.dto.response.BarcodeLookUp.InfoProduct;

public interface BarcodeLookUpService {
    InfoProduct getInfoProduct(String barcode);
}
