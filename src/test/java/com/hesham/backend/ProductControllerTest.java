package com.hesham.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hesham.backend.controller.ProductController;
import com.hesham.backend.model.Product;
import com.hesham.backend.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {
    ObjectMapper mapper;
    private MockMvc mockMvc;
    @MockBean
    ProductService productService;

    @InjectMocks
    ProductController productController;

    @BeforeEach
    public void contextLoad(){
        mapper = new ObjectMapper();
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void testGetAllProducts() throws Exception {

        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product(1l, "iphone 8", 450.50
                , 6, "New Gold Iphone 8", ""));

        when(productService.findAllProducts()).thenReturn(products);

        mockMvc.perform(get("/product/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$[0].name").value("iphone 8"))
                .andExpect(jsonPath("$[0].price").value(450.50))
                .andExpect(jsonPath("$[0].quantity").value(6));
    }
}
