package com.spring_react_demo.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring_react_demo.demo.dto.UserRegistrationDTO;
import com.spring_react_demo.demo.entity.User;
import com.spring_react_demo.demo.exception.EmailAlreadyTakenException;
import com.spring_react_demo.demo.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserService userService;

    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @ExceptionHandler({EmailAlreadyTakenException.class})
    public ResponseEntity<String> handleEmailTaken() {
        return new ResponseEntity<String>("The email address you provided is already in use", HttpStatus.CONFLICT);
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody UserRegistrationDTO userRegDTO) {
        return userService.registerUser(userRegDTO);
    }

}
