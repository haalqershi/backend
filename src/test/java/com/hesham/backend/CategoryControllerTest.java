package com.hesham.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hesham.backend.controller.CategoryController;
import com.hesham.backend.model.Category;
import com.hesham.backend.model.Product;
import com.hesham.backend.service.CategoryService;
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
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {
    ObjectMapper mapper;
    private MockMvc mockMvc;
    @MockBean
    CategoryService categoryService;

    @InjectMocks
    CategoryController categoryController;

    @BeforeEach
    public void contextLoad(){
        mapper = new ObjectMapper();
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    void testGetAllCategories() throws Exception {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1l, "books",
                Arrays.asList(new Product(1l, "design pattern"
                        , 88.8, 3, "design pattern by Hesham", ""))));

        when(categoryService.findAllCategories()).thenReturn(categories);

        mockMvc.perform(get("/category/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$[0].categoryName").value("books"));
    }


}
