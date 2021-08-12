package com.hesham.backend.service;

import com.hesham.backend.enumeration.Role;
import com.hesham.backend.exception.EmailExistException;
import com.hesham.backend.exception.UserNotFoundException;
import com.hesham.backend.exception.UsernameExistException;
import com.hesham.backend.model.User;
import com.hesham.backend.repository.UserRepository;
import com.hesham.backend.security.UserPrincipal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@Qualifier("userDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {


    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findUserByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("User with username : " + username  + " not found");
        }else{
            UserPrincipal userPrincipal = new UserPrincipal(user);
            return userPrincipal;
        }
    }

    @Override
    public User registerNewUser(String firstName, String lastName, String username, String email, String password) throws UserNotFoundException
            , EmailExistException, UsernameExistException {
        areValidUsernameAndEmail(StringUtils.EMPTY, username, email);
        User newUser = new User();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setActive(true);
        newUser.setNotLocked(true);
        newUser.setLastLoginDate(new Date());
        newUser.setRoles(Role.ROLE_USER.name());
        newUser.setAuthorities(Role.ROLE_USER.getAuthorities());
        return this.userRepository.save(newUser);
    }




    private User areValidUsernameAndEmail(String currentUsername, String newUsername, String email) throws UserNotFoundException, UsernameExistException, EmailExistException {

        User newUser = findUserByUsername(newUsername);
        User userByEmail = findUserByUsername(email);

        if(StringUtils.isNotBlank(currentUsername)){
            User currentUser = findUserByUsername(currentUsername);
            if(currentUser == null){
                throw new UserNotFoundException("No user exist with the username: " + currentUsername);
            }

            if(newUser != null && currentUser.getId() != newUser.getId()){
                throw new UsernameExistException("The username is already exist!");
            }

            if(userByEmail != null && currentUser.getId() != userByEmail.getId()){
                throw new EmailExistException("The email is already exist!");
            }
            return currentUser;
        }else{
            if(newUser != null){
                throw new UsernameExistException("The username is already exist!");
            }
            if(userByEmail != null){
                throw new EmailExistException("The email is already exist!");
            }
            return null;
        }
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
}
