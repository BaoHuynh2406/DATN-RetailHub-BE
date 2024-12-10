package com.project.retailhub.data.mapper;

import com.project.retailhub.data.dto.request.product.ProductRequest;
import com.project.retailhub.data.dto.response.Category.CategoryResponse;
import com.project.retailhub.data.dto.response.product.ProductResponse;
import com.project.retailhub.data.dto.response.Tax.TaxResponse;
import com.project.retailhub.data.entity.Category;
import com.project.retailhub.data.entity.Product;
import com.project.retailhub.data.entity.Tax;
import com.project.retailhub.data.repository.CategoryRepository;
import com.project.retailhub.data.repository.TaxRepository;
import com.project.retailhub.exception.AppException;
import com.project.retailhub.exception.ErrorCode;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toProduct(ProductRequest request);

    @Mapping(source = "categoryId", target = "category", qualifiedByName = "categoryToCategoryResponse")
    @Mapping(source = "taxId", target = "tax", qualifiedByName = "taxToTaxResponse")
    ProductResponse toProductResponse(Product product, @Context CategoryRepository categoryRepository, @Context TaxRepository taxRepository);

    @Named("categoryToCategoryResponse")
    default CategoryResponse mapCategoryToCategoryResponse(int categoryId, @Context CategoryRepository categoryRepository) {
        Category e = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        return CategoryResponse.builder()
                .categoryId(e.getCategoryId())
                .categoryName(e.getCategoryName())
                .build();
    }

    @Named("taxToTaxResponse")
    default TaxResponse mapTaxToTaxResponse(String taxId, @Context TaxRepository taxRepository) {
        Tax e = taxRepository.findById(taxId)
                .orElseThrow(() -> new AppException(ErrorCode.TAX_NOT_FOUND));
        return TaxResponse.builder()
                .taxId(e.getTaxId())
                .taxName(e.getTaxName())
                .taxRate(e.getTaxRate())
                .build();
    }

    List<ProductResponse> toProductResponseList(List<Product> products, @Context CategoryRepository categoryRepository, @Context TaxRepository taxRepository);

    List<Product> toProductsList(List<ProductRequest> requests);
}
