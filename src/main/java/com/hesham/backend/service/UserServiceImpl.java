package com.hesham.backend.service;

import com.hesham.backend.controller.CategoryController;
import com.hesham.backend.exception.UserNotFoundException;
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
    public User updateUser(long id, User user) throws UserNotFoundException {
        User updatedUser = this.userRepository.findById(id).orElse(null);
        if(updatedUser == null){
            throw new UserNotFoundException("user with Id: " + id);
        }
        updatedUser.setUsername(user.getUsername());
        updatedUser.setPassword(user.getPassword());
        updatedUser.setFirstName(user.getFirstName());
        updatedUser.setLastName(user.getLastName());
		return this.userRepository.save(updatedUser);
    	
    }
    
    @Override
    public void deleteUserbyId(Long id) {
    	this.userRepository.deleteById(id);
    }
}
