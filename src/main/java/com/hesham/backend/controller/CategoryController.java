package com.hesham.backend.controller;

import com.hesham.backend.model.Category;
import com.hesham.backend.model.Product;
import com.hesham.backend.model.UpdateCategory;
import com.hesham.backend.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PutMapping("/update")
    @ApiOperation(value = "Update a category", notes = "used to update a category", response = Category.class)
    public ResponseEntity<Category> updateCategory(@RequestBody UpdateCategory updateCategory){
        Category updatedCategory = this.categoryService.updateCategory(updateCategory);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Delete a category", notes = "used to delete a category by id", response = Category.class)
    public ResponseEntity<?> deleteCategory(@PathVariable Long id){
        this.categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    @ApiOperation(value = "Find a category", notes = "used to retrieve a category by id", response = Category.class)
    public ResponseEntity<Category> getProductById(@PathVariable("id") Long id){
        Category category = this.categoryService.findCategoryById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }
}
