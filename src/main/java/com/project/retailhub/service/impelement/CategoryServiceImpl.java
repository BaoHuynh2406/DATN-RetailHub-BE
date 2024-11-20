package com.project.retailhub.service.impelement;

import com.project.retailhub.data.dto.request.Category.CategoryRequest;
import com.project.retailhub.data.dto.response.Category.CategoryResponse;
import com.project.retailhub.data.entity.Category;
import com.project.retailhub.data.mapper.CategoryMapper;
import com.project.retailhub.data.repository.CategoryRepository;
import com.project.retailhub.exception.AppException;
import com.project.retailhub.exception.ErrorCode;
import com.project.retailhub.service.CategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryServiceImpl implements CategoryService {

    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);
    CategoryRepository CategoryRepository;
    CategoryMapper CategoryMapper;

    @Override
    public void addNewCategory(CategoryRequest request) {
        if (CategoryRepository.existsByCategoryName(request.getCategoryName()))
            throw new AppException(ErrorCode.CATEGORY_NAME_ALREADY_EXIST);

        // Thực hiện chuyển đổi request thành entity
        Category Category = CategoryMapper.toCategory(request);
        CategoryRepository.save(Category);
    }

    @Override
    public void updateCategory(CategoryRequest request) {
        int categoryId = request.getCategoryId();  // Chỉnh sửa để lấy categoryId dạng int
        if (categoryId <= 0) {
            throw new AppException(ErrorCode.CATEGORY_ID_NULL);
        }

        // Tìm kiếm Category theo ID
        Category Category = CategoryRepository.findById(categoryId)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        // Cập nhật thông tin Category
        Category.setCategoryName(request.getCategoryName());

        // Lưu thông tin Category đã cập nhật
        CategoryRepository.save(Category);
    }

    @Override
    public void deleteCategory(int categoryId) {
        Category Category = CategoryRepository.findById(categoryId)  // Không cần chuyển kiểu dữ liệu
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        Category.setIsDelete(true);
        CategoryRepository.save(Category);
    }

    @Override
    public void restoreCategory(int categoryId) {
        Category Category = CategoryRepository.findById(categoryId)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        Category.setIsDelete(false);
        CategoryRepository.save(Category);
    }

    @Override
    public List<CategoryResponse> findAllActiveCategories()   {
        return CategoryMapper.toCategoryResponseList(CategoryRepository.findAllByIsDelete(false));
    }

    @Override
    public List<CategoryResponse> findAllCategories() {
        return CategoryMapper.toCategoryResponseList(CategoryRepository.findAll());
    }

    @Override
    public CategoryResponse findCategoryByCategoryId(int  categoryId) {
        Category Category = CategoryRepository.findById(categoryId)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        return CategoryMapper.toCategoryResponse(Category);
    }
}
