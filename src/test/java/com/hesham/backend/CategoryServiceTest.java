package com.hesham.backend;

import com.hesham.backend.model.Category;
import com.hesham.backend.model.Product;
import com.hesham.backend.repository.CategoryRepository;
import com.hesham.backend.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
public class CategoryServiceTest {

//    public List<Category> findAllCategories() {
//        return this.categoryRepository.findAll();
//    }
//
//    public Category save(Category category) {
//        return this.categoryRepository.save(category);
//    }
    @MockBean
    CategoryRepository categoryRepository;

    @Autowired
    CategoryService categoryService;

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

        categories.add(new Category("cell phones", Arrays.asList(new Product(1l, "Iphone xr"
                , 450.70, 107, "Iphone xr 64GB", ""))));

        assertEquals(categories.size(), 2);
        assertEquals(categories.get(1).getCategoryName(), "cell phones");
        assertEquals(categories.get(1).getProducts().size(), 1);

    }

    @Test
    public void testAddCategory(){

    }



}
