package com.hesham.backend.controller;

import com.hesham.backend.exception.UserNotFoundException;
import com.hesham.backend.model.AuthenticationRequest;
import com.hesham.backend.model.AuthenticationResponse;
import com.hesham.backend.model.User;
import com.hesham.backend.service.MyUserDetailsService;
import com.hesham.backend.service.UserService;
import com.hesham.backend.util.JwtUtil;
import io.swagger.annotations.ApiOperation;
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
public class UserController {


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
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername()
                    , authenticationRequest.getPassword()));
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
    public User createAuthenticationToken(@RequestBody User user){
        User newUser = this.userService.registerNewUser(user);
        return newUser;
    }

    @GetMapping("/user/all")
    @ApiOperation(value = "Find List of Users", notes = "used to retrieve list of Users", response = User.class)
    public ResponseEntity<List<User>> getUsers(){
        List<User> users = this.userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

}
