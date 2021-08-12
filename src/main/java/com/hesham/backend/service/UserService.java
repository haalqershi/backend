package com.hesham.backend.service;

import com.hesham.backend.exception.EmailExistException;
import com.hesham.backend.exception.UserNotFoundException;
import com.hesham.backend.exception.UsernameExistException;
import com.hesham.backend.model.User;

import java.util.List;

public interface UserService {

    User registerNewUser(String firstName, String lastName, String username, String email, String password) throws UserNotFoundException, EmailExistException, UsernameExistException;

    List<User> getAllUsers();

    User findUserByUsername(String username);

    User findUserByEmail(String email);

}
