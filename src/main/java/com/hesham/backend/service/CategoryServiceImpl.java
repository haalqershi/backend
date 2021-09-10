package com.hesham.backend.service;

import com.hesham.backend.controller.CategoryController;
import com.hesham.backend.model.Category;
import com.hesham.backend.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{
    private static Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAllCategories() {
        return this.categoryRepository.findAll();
    }

    public Category save(Category category) {
        return this.categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category) {
        Category updatedCategory = this.categoryRepository.findById(category.getId()).orElse(null);
        updatedCategory.setCategoryName(category.getCategoryName());
        return this.categoryRepository.save(updatedCategory);
    }
}
