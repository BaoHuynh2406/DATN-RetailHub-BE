package com.project.retailhub.api;

import com.project.retailhub.data.dto.request.Category.CategoryRequest;
import com.project.retailhub.data.dto.response.Category.CategoryResponse;
import com.project.retailhub.data.dto.response.ResponseObject;
import com.project.retailhub.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryAPI {

    final CategoryService categoryService;

    @GetMapping("/getAllActive")
    public ResponseObject<?> doGetFindAllActiveCategories() {
        var resultApi = new ResponseObject<>();
        resultApi.setData(categoryService.findAllActiveCategories());
        log.info("Get ALL Active Categories");
        return resultApi;
    }

    @GetMapping("/getAll")
    public ResponseObject<?> doGetFindAllCategories(){
        var resultApi = new ResponseObject<>();
        resultApi.setData(categoryService.findAllCategories());
        log.info("Get ALL Categories");
        return resultApi;
    }

    @GetMapping("/{categoryId}")
    public ResponseObject<?> doGetCategoryById(@PathVariable("categoryId") int categoryId) {
        var resultApi = new ResponseObject<>();
        resultApi.setData(categoryService.findCategoryByCategoryId(categoryId));
        log.info("Get category with ID " + categoryId);
        return resultApi;
    }

    @PostMapping("/create")
    public ResponseObject<?> doPostCreateCategory(@RequestBody CategoryRequest request) {
        var resultApi = new ResponseObject<>();
        categoryService.addNewCategory(request);
        resultApi.setMessage("Category added successfully");
        log.info("Added category successfully");
        return resultApi;
    }

    @PutMapping("/update")
    public ResponseObject<?> doPostUpdateCategory(@RequestBody CategoryRequest request) {
        var resultApi = new ResponseObject<>();
        categoryService.updateCategory(request);
        resultApi.setMessage("Category updated successfully");
        log.info("Updated category with ID " + request.getCategoryId() + " successfully");
        return resultApi;
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseObject<?> doDeleteCategory(@PathVariable("categoryId") int categoryId) {
        var resultApi = new ResponseObject<>();
        categoryService.deleteCategory(categoryId);
        resultApi.setMessage("Category deleted successfully");
        log.info("Deleted category with ID " + categoryId);
        return resultApi;
    }

    @PutMapping("/restore/{categoryId}")
    public ResponseObject<?> doRestoreCategory(@PathVariable("categoryId") int categoryId) {
        var resultApi = new ResponseObject<>();
        categoryService.restoreCategory(categoryId);
        resultApi.setMessage("Category restored successfully");
        log.info("Restored category with ID " + categoryId);
        return resultApi;
    }
}
