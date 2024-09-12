package com.project.retailhub.service;

import com.project.retailhub.data.dto.request.Category.CategoryRequest;
import com.project.retailhub.data.dto.response.Category.CategoryResponse;

import java.util.List;
public interface CategoryService
{

    void addNewCategory(CategoryRequest request);

    void updateCategory(CategoryRequest request);

    void deleteCategory(int categoryId);

    CategoryResponse findCategoryByCategoryId(int  categoryId);

    void restoreCategory(int  categoryId);

    List<CategoryResponse> findAllActiveCategories();

    List<CategoryResponse> findAllCategories();

}
