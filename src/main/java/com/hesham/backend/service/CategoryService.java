package com.hesham.backend.service;

import com.hesham.backend.model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> findAllCategories();

    Category save(Category category);
}
