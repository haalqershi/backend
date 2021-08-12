package com.hesham.backend.controller;

import com.hesham.backend.exception.EmailExistException;
import com.hesham.backend.exception.MyExceptionHandler;
import com.hesham.backend.exception.UserNotFoundException;
import com.hesham.backend.exception.UsernameExistException;
import com.hesham.backend.model.User;
import com.hesham.backend.security.SecurityConstants;
import com.hesham.backend.security.TokenProvider;
import com.hesham.backend.security.UserPrincipal;
import com.hesham.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = { "/", "/user"})
public class UserController extends MyExceptionHandler {

    private UserService userService;
    private AuthenticationManager authenticationManager;
    private TokenProvider tokenProvider;

    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager
            , TokenProvider tokenProvider) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) throws UserNotFoundException, EmailExistException, UsernameExistException {
        User newUser =  this.userService.registerNewUser(user.getFirstName(), user.getLastName()
                ,user.getUsername(), user.getEmail(), user.getPassword());
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) throws UserNotFoundException, EmailExistException, UsernameExistException {
        authenticate(user.getUsername(), user.getPassword());
        User authenticateUser = this.userService.findUserByUsername(user.getUsername());
        UserPrincipal userPrincipal = new UserPrincipal(authenticateUser);

        // generate the header for the JWT
        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
        return new ResponseEntity<>(authenticateUser, jwtHeader, HttpStatus.OK);
    }

    // adding JWT to the header
    private HttpHeaders getJwtHeader(UserPrincipal userPrincipal) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(SecurityConstants.JWT_TOKEN_HEADER, tokenProvider.generateJwtToken(userPrincipal));
        return headers;
    }

    // authenticate
    private void authenticate(String username, String password) {
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

}
