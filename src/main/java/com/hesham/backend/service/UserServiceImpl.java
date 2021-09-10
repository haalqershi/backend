package com.hesham.backend.service;

import com.hesham.backend.controller.CategoryController;
import com.hesham.backend.model.User;
import com.hesham.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUsername(user.getUsername());
        User newUser =  this.userRepository.save(user);
        return newUser;
    }

    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public User findUserByUsername(String username) {
        return this.userRepository.findUserByUsername(username);
    }

    @Override
    public User findUserByEmail(String email) {
        return this.userRepository.findUserByEmail(email);
    }
    
    @Override
    public User updateUser(String username, String password, String firstName, String lastName) {
    	User userUpdated = new User();
    	userUpdated.setUsername(username);
    	userUpdated.setPassword(passwordEncoder.encode(userUpdated.getPassword()));
    	userUpdated.setFirstName(firstName);
    	userUpdated.setLastName(lastName);
		return this.userRepository.save(userUpdated);
    	
    }
    
    // Need DeleteUser method
    
}
