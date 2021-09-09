package com.hesham.backend;

import com.hesham.backend.model.User;
import com.hesham.backend.repository.UserRepository;
import com.hesham.backend.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Date;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {
    @MockBean
    UserRepository userRepository;

    @MockBean
    PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    private User user;

    @BeforeEach
    public void init(){
        user = new User(1l,"Hesham", "Alqershi", "hesham"
                , "hesham_alqershi@hcl.com", passwordEncoder.encode("123"), null, true
                , true, new Date(), null);
    }

    @AfterEach
    public void after(){
        user = null;
    }
}
