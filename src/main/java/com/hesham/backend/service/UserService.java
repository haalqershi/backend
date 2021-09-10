package com.hesham.backend.service;

import com.hesham.backend.exception.UserNotFoundException;
import com.hesham.backend.model.User;

import java.util.List;

public interface UserService {

    User registerNewUser(User user);

    List<User> getAllUsers();

    User findUserByUsername(String username);

    User findUserByEmail(String email);
    
    User updateUser(long id, User user) throws UserNotFoundException;
    
    void deleteUserbyId(Long id);
}
