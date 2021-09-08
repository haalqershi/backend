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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

    @Test
    public void testFindAllProducts(){
        List<Product> productList = new ArrayList<>();
        productList.add(new Product(2l, "Iphone XR"
                , 450.70, 107, "Iphone XR 64GB", ""));

        when(productRepository.findAll()).thenReturn(productList);
        List<Product> products = productService.findAllProducts();
        assertEquals(products.size(), 1);
        assertEquals(products.get(0).getName(), "Iphone XR");
        assertEquals(products.get(0).getPrice(), 450.70);

        productList.add(newProduct);
        assertEquals(products.size(), 2);
        assertEquals(products.get(1).getName(), "Thinkpad");
        assertEquals(products.get(1).getPrice(), 1858.00);
    }

    @Test
    public void testUpdateProduct(){
        when(productRepository.save(ArgumentMatchers.any(Product.class))).thenReturn(newProduct);
        Product product = productService.updateProduct(new Product());

        assertNotNull(product);
    }

    @Test
    public void testFindProductById(){
        when(productRepository.findProductById(1l)).thenReturn(Optional.of(newProduct));
        Product product = productService.findProductById(1l);

        assertNotNull(product);
        verify(productRepository, times(1)).findProductById(product.getId());
    }

    @Test
    public void testDeleteProduct(){

    }

}
