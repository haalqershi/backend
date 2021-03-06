package com.hesham.backend.service;

import com.hesham.backend.model.Category;
import com.hesham.backend.model.UpdateCategory;
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
    public Category updateCategory(UpdateCategory updateCategory) {
        Category category = this.categoryRepository.findById(updateCategory.getId()).orElse(null);
        if(category == null){
            throw new NullPointerException("No categeory esit with id " + updateCategory.getId());
        }
        category.setCategoryName(updateCategory.getCategoryName());
        logger.info("update category with name: %s", updateCategory.getCategoryName());

        return this.categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        this.categoryRepository.deleteById(id);
    }

    @Override
    public Category findCategoryById(Long id) {
        return this.categoryRepository.findById(id).orElse(null);
    }


}
