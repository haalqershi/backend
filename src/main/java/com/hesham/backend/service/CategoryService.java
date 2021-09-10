package com.hesham.backend.service;

import com.hesham.backend.model.Category;
import com.hesham.backend.model.Product;

import java.util.List;

public interface CategoryService {

    List<Category> findAllCategories();

    Category save(Category category);

    Category updateCategory(Category category);

    void deleteCategory(Long id);

    Category findCategoryById(Long id);
}
