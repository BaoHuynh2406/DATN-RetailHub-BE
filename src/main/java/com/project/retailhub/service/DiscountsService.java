package com.project.retailhub.service;

import com.project.retailhub.data.dto.response.Pagination.PageResponse;
import com.project.retailhub.data.entity.Discounts;

import java.util.List;


public interface DiscountsService {

     PageResponse<Discounts> getAllDiscounts ();

    List<Discounts> getAllDiscountsAvailable();

    Discounts getDiscountByProductIdAvailable (long productId);

    void addDiscount (Discounts discount);

    void deleteDiscount (Long id);

    Discounts updateDiscount (Discounts updatedDiscount);
}
