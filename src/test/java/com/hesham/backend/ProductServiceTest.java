package com.hesham.backend;

import com.hesham.backend.model.Category;
import com.hesham.backend.model.Product;
import com.hesham.backend.repository.CategoryRepository;
import com.hesham.backend.repository.ProductRepository;
import com.hesham.backend.service.CategoryService;
import com.hesham.backend.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductServiceTest {
    @MockBean
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    @MockBean
    CategoryRepository categoryRepository;

    @Autowired
    CategoryService categoryService;

    private Product newProduct;

    @BeforeEach
    public void loadProducts(){
       newProduct = new Product(1l, "Thinkpad"
               , 1858.00, 12, "Thinkpad 1T SSD Black E590", "");
    }

    @Test
    public void testAddProduct(){
        when(categoryRepository.save(ArgumentMatchers.any(Category.class)))
                .thenReturn(new Category("computers", null));
        Category category = categoryService.save(new Category());

        when(productRepository.save(ArgumentMatchers.any(Product.class))).thenReturn(newProduct);
        Product product = productService.addProduct(new Product(), 1l);

        assertNotNull(product);
    }
}
