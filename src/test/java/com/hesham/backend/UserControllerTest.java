package com.hesham.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hesham.backend.controller.UserController;
import com.hesham.backend.service.UserService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    ObjectMapper mapper;
    private MockMvc mockMvc;
    @MockBean
    UserService userService;

    @InjectMocks
    UserController userController;

    @BeforeEach
    public void contextLoad(){
        mapper = new ObjectMapper();
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

//    @Test
//    public void testRegisterUser() throws Exception {
//        User user = new User("Hesham", "Alqershi", "hesham"
//                , "hesham_alqershi@hcl.com", "123", null, true
//                , true, null, null);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/register")
//                        .content(asJsonString(user))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.password").exists());
//
//    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
