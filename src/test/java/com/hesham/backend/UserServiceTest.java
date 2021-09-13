package com.hesham.backend;

import com.hesham.backend.model.User;
import com.hesham.backend.repository.UserRepository;
import com.hesham.backend.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
class UserServiceTest {
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
                , "hesham_alqershi@hcl.com", passwordEncoder.encode("123"), "User", null,true
                , true, new Date());
    }

    @AfterEach
    public void after(){
        user = null;
    }

    @Test
    void testGetAllUsers(){
        List<User> userList = new ArrayList<>();
        userList.add(user);

        when(userRepository.findAll()).thenReturn(userList);

        List<User> users = userService.getAllUsers();
        assertEquals(1, users.size());
        assertEquals("Hesham", users.get(0).getFirstName());

        userList.add(new User(1l,"John", "Smith", "john123"
                , "john123@hcl.com", "123","User", null, true
                , true, new Date()));

        users = userService.getAllUsers();
        assertEquals(2, users.size());
        assertEquals("John", users.get(1).getFirstName());
    }

    @Test
    void testRegisterNewUser(){
//        when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(user);
//
//        User newUser = userService.registerNewUser(new User());
//
//        assertNotNull(newUser);
//        assertEquals(newUser.getId(), 1l);
//        assertEquals(newUser.getUsername(), "hesham");
    }

    @Test
    void testFindUserByUsername(){
        when(userRepository.findUserByUsername(ArgumentMatchers.any(String.class))).thenReturn(user);
        User newUser = userService.findUserByUsername("hesham");

        assertNotNull(newUser);
        assertEquals("hesham", newUser.getUsername());
    }

    @Test
    void findUserByEmail(){
        when(userRepository.findUserByEmail(ArgumentMatchers.any(String.class))).thenReturn(user);
        User newUser = userService.findUserByEmail("hesham_alqershi@hcl.com");

        assertNotNull(newUser);
        assertEquals("hesham_alqershi@hcl.com", newUser.getEmail());
    }

    @Test
    void testDeleteUserById(){
        doNothing().when(userRepository).deleteById(1l);
        userService.deleteUserbyId(1l);
        verify(userRepository, times(1)).deleteById(1l);
    }
}
