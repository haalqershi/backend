package com.hesham.backend.service;

import com.hesham.backend.model.User;

import java.util.List;

public interface UserService {

    User registerNewUser(String username, String password);

    List<User> getAllUsers();

    User findUserByUsername(String username);

    User findUserByEmail(String email);

}
