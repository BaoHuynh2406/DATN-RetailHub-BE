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
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "categoryId", target = "category", qualifiedByName = "categoryIdToCategory")
    @Mapping(source = "taxId", target = "tax", qualifiedByName = "taxIdToTax")
    Product toProduct(ProductRequest request, @Context CategoryRepository categoryRepository, @Context TaxRepository taxRepository);

    @Named("categoryIdToCategory")
    default Category mapCategoryIdToCategory(int categoryId, @Context CategoryRepository categoryRepository) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @Named("taxIdToTax")
    default Tax mapTaxIdToTax(String taxId, @Context TaxRepository taxRepository) {
        return taxRepository.findById(taxId)
                .orElseThrow(() -> new RuntimeException("Tax not found"));
    }

    @Mapping(source = "category", target = "category", qualifiedByName = "categoryToCategoryResponse")
    @Mapping(source = "tax", target = "tax", qualifiedByName = "taxToTaxResponse")
    ProductResponse toProductResponse(Product product);

    @Named("categoryToCategoryResponse")
    default CategoryResponse mapCategoryToCategoryResponse(Category category) {
        if (category == null) {
            return null;
        }
        return CategoryResponse.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .build();
    }

    @Named("taxToTaxResponse")
    default TaxResponse mapTaxToTaxResponse(Tax tax) {
        if (tax == null) {
            return null;
        }
        return TaxResponse.builder()
                .taxId(tax.getTaxId())
                .taxName(tax.getTaxName())
                .build();
    }

    List<ProductResponse> toProductResponseList(List<Product> products);

    List<Product> toProductsList(List<ProductRequest> requests);
}
