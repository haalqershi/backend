package com.hesham.backend;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hesham.backend.model.User;
import com.hesham.backend.service.UserService;
@SpringBootTest
public class UserSeviceImplTest {
	@Autowired
	UserService service;
	
	@Test
	public void testRegister() {
		User p = new User(1l, "a","a", "abc", "abc@gmail.com", "123", null, null, false, false, null);
		
		service.registerNewUser("abc", "123");
		
		User p1 = service.findUserByUsername("abc");
		
		assertEquals("abc", p1.getUsername());
	}

}
