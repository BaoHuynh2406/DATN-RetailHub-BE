package com.project.retailhub.data.mapper;


import com.project.retailhub.data.dto.response.DiscountResponse;
import com.project.retailhub.data.entity.Discounts;
import org.mapstruct.Mapper;



@Mapper(componentModel = "spring")
public interface DiscountMapper {

    DiscountResponse toDiscountResponse(Discounts discount);
}
