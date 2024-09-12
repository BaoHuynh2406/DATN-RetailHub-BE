package com.project.retailhub.data.mapper;
import com.project.retailhub.data.dto.request.Category.CategoryRequest;
import com.project.retailhub.data.dto.response.Category.CategoryResponse;
import com.project.retailhub.data.entity.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryRequest request);

    // Entity to Response
    CategoryResponse toCategoryResponse(Category category);

    // Phương thức chuyển đổi danh sách Category thành danh sách CategoryResponse
    List<CategoryResponse> toCategoryResponseList(List<Category> categories);

    // Phương thức chuyển đổi danh sách CategoryRequest thành danh sách Category
    List<Category> toCategoriesList(List<CategoryRequest> requests);
}
