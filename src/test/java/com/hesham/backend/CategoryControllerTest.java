package com.hesham.backend;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hesham.backend.model.Category;
import com.hesham.backend.model.Product;

@SpringBootTest
class CategoryControllerTest {
	@Autowired
	private WebApplicationContext webApplicationContext;
	private MockMvc mvc;

	@BeforeEach
	public void getContext() {
		mvc = webAppContextSetup(webApplicationContext).build();
	}
	@Test
	void getAllproducts() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/category/all")
				.accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk());
	}
	@Test
	void createCategory() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/category/add")
				.content(asJasonString(new Category("Camera", List<product>)))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.categoryName").exists());
		mvc.perform(MockMvcRequestBuilders.get("/category/all/{id})",1)
				.accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
	}
	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
