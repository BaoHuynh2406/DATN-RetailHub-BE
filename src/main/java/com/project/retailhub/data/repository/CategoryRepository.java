package com.project.retailhub.data.repository;

import com.project.retailhub.data.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findAllByIsDelete(boolean isDelete);
    boolean existsByCategoryName(String CategoryName);
}
