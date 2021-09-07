package com.hesham.backend;

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
import com.hesham.backend.model.Product;
import com.hesham.backend.model.User;

@SpringBootTest
class UserControllerTest {
	@Autowired
	private WebApplicationContext webApplicationContext;
	private MockMvc mvc;
	@BeforeEach
	public void getContext() {
		mvc = webAppContextSetup(webApplicationContext).build();
	}
	@Test
	public void createUserAPI() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/register/{id}")
				.content(asJsonString(new User(1l,"userName4","email4@mail.com", "123", "NA", "NA", "NA", null, true, true, null)))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
		
		mvc.perform(MockMvcRequestBuilders.get("/login",1)
				.accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$id").value(1));
		
	}
	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
