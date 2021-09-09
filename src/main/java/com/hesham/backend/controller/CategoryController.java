package com.hesham.backend.controller;

import com.hesham.backend.model.Category;
import com.hesham.backend.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/category")
@Api(value = "Category Resource REST Endpoint")
public class CategoryController {

    private static Logger logger = LoggerFactory.getLogger(CategoryController.class);

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/add")
    @ApiOperation(value = "Add a new category", notes = "used to create a new category", response = Category.class)
    public Category addNewCategory(@RequestBody Category category){
        logger.info("Adding new category");
        Category newCategory = new Category(category.getCategoryName(), new ArrayList<>());
        return this.categoryService.save(newCategory);
    }

    @GetMapping("/all")
    @ApiOperation(value = "Find List of Categories", notes = "used to retrieve list of categories", response = Category.class)
    public List<Category> getAllCategories(){
        logger.info("retrive all categories");
        return this.categoryService.findAllCategories();
    }
}
