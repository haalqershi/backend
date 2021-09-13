package com.hesham.backend.controller;

import com.hesham.backend.exception.UserNotFoundException;
import com.hesham.backend.model.AuthenticationRequest;
import com.hesham.backend.model.AuthenticationResponse;
import com.hesham.backend.model.User;
import com.hesham.backend.service.MyUserDetailsService;
import com.hesham.backend.service.UserService;
import com.hesham.backend.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@Api(value = "User Resource REST Endpoint")
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    private AuthenticationManager authenticationManager;
    private MyUserDetailsService myUserDetailsService;
    private JwtUtil jwtToken;
    private UserService userService;

    @Autowired
    public UserController(AuthenticationManager authenticationManager, MyUserDetailsService myUserDetailsService, JwtUtil jwtToken, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.myUserDetailsService = myUserDetailsService;
        this.jwtToken = jwtToken;
        this.userService = userService;
    }

    @PostMapping("/login")
    @ApiOperation(value = "Login an active user", notes = "used to login a user", response = ResponseEntity.class)
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername()
                    , authenticationRequest.getPassword()));
            logger.info("logging with username: " + authenticationRequest.getUsername());
        } catch (BadCredentialsException ex){
            throw new UserNotFoundException("User Not Found : " + ex);
        }

        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtToken.generateToken(userDetails);
        HttpHeaders jwtHeader = new HttpHeaders();
        jwtHeader.add("Jwt-Token", new AuthenticationResponse(jwt).getJwt());
        return new ResponseEntity<>(authenticationRequest , jwtHeader, HttpStatus.OK);
    }

    @PostMapping("/register")
    @ApiOperation(value = "Register a new user", notes = "used to register a new user", response = User.class)
    public User register(@RequestBody User user){
        User newUser = this.userService.registerNewUser(user);
        return newUser;
    }

    @GetMapping("/user/all")
    @ApiOperation(value = "Find List of Users", notes = "used to retrieve list of Users", response = User.class)
    public ResponseEntity<List<User>> getUsers(){
        List<User> users = this.userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @DeleteMapping("/user/delete/{id}")
    @ApiOperation(value = "Delete a User", notes = "used to delete a User by id", response = User.class)
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        this.userService.deleteUserbyId(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @ApiOperation(value = "Update a user", notes = "used to update a user", response = User.class)
    public User updateUser(@PathVariable long id, @RequestBody User user) throws UserNotFoundException {
        return this.userService.updateUser(id, user);
    }

}
