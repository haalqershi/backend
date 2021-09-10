package com.hesham.backend;

import com.hesham.backend.model.Category;
import com.hesham.backend.model.Product;
import com.hesham.backend.model.UpdateCategory;
import com.hesham.backend.repository.CategoryRepository;
import com.hesham.backend.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
public class CategoryServiceTest {

    @MockBean
    CategoryRepository categoryRepository;

    @Autowired
    CategoryService categoryService;

    private Category newCategory;

    @BeforeEach
    public void loadCategories(){
        newCategory = new Category(1l, "cell phones", Arrays.asList(new Product(1l, "Iphone xr"
                , 450.70, 107, "Iphone xr 64GB", "")));
    }

    @Test
    public void testFindAllCategories(){
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category("computers", Arrays.asList(new Product(1l, "Thinkpad"
                        , 1858.00, 12, "Thinkpad 1T SSD Black E590", ""),
                new Product(2l, "HP S50", 658.00, 40
                        , "HP S50 Black 32GB", ""))));

        when(categoryRepository.findAll()).thenReturn(categoryList);

        List<Category> categories = categoryService.findAllCategories();

        assertEquals(categories.size(), 1);
        assertEquals(categories.get(0).getCategoryName(), "computers");
        assertEquals(categories.get(0).getProducts().size(), 2);

        categories.add(newCategory);

        assertEquals(categories.size(), 2);
        assertEquals(categories.get(1).getCategoryName(), "cell phones");
        assertEquals(categories.get(1).getProducts().size(), 1);
    }

    @Test
    public void testAddCategory(){
        when(categoryRepository.save(ArgumentMatchers.any(Category.class))).thenReturn(newCategory);

        Category category = categoryService.save(new Category());

        assertNotNull(category);
        assertEquals(category.getCategoryName(), "cell phones");
    }

    @Test
    public void testUpdateCategory(){
        when(categoryRepository.save(ArgumentMatchers.any(Category.class))).thenReturn(newCategory);
        Category category = categoryService.updateCategory(new UpdateCategory());

        assertNotNull(category);
    }

    @Test
    public void testDeleteCategory(){
        doNothing().when(categoryRepository).deleteById(1l);
        categoryService.deleteCategory(1l);
        verify(categoryRepository, times(1)).deleteById(1l);
    }

    @Test
    public void testFindCategoryById(){
        when(categoryRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.of(newCategory));
        Category category = categoryService.findCategoryById(1l);

        assertNotNull(category);
        verify(categoryRepository, times(1)).findById(category.getId());
    }
}
