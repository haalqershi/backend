package com.hesham.backend.service;

import com.hesham.backend.model.Category;
import com.hesham.backend.model.Product;
import com.hesham.backend.model.UpdateCategory;

import java.util.List;

public interface CategoryService {

    List<Category> findAllCategories();

    Category save(Category category);

    Category updateCategory(UpdateCategory updateCategory);

    void deleteCategory(Long id);

    Category findCategoryById(Long id);
}
