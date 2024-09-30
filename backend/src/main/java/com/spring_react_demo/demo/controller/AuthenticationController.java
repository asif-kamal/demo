package com.spring_react_demo.demo.controller;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring_react_demo.demo.dto.UserRegistrationDTO;
import com.spring_react_demo.demo.entity.User;
import com.spring_react_demo.demo.exception.EmailAlreadyTakenException;
import com.spring_react_demo.demo.exception.EmailFailedToSendException;
import com.spring_react_demo.demo.exception.IncorrectVerificationCodeException;
import com.spring_react_demo.demo.exception.UserDoesNotExistException;
import com.spring_react_demo.demo.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserService userService;

    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @ExceptionHandler({ EmailAlreadyTakenException.class })
    public ResponseEntity<String> handleEmailTaken() {
        return new ResponseEntity<String>("The email address you provided is already in use", HttpStatus.CONFLICT);
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody UserRegistrationDTO userRegDTO) {
        return userService.registerUser(userRegDTO);
    }

    @ExceptionHandler({ UserDoesNotExistException.class })
    public ResponseEntity<String> handleUserDoesNotExist() {
        return new ResponseEntity<String>("The user you are looking for does not exist", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update/phone")
    public User updatePhoneNumber(@RequestBody LinkedHashMap<String, String> body) {

        String username = body.get("username");
        String phone = body.get("phone");

        User user = userService.getUserByUsername(username);

        user.setPhoneNumber(phone);

        return userService.updateUser(user);
    }

    @ExceptionHandler({ EmailFailedToSendException.class })
    public ResponseEntity<String> handleFailedEmail() {
        return new ResponseEntity<String>("Email failed to send, try again in a minute",
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/email/code")
    public ResponseEntity<String> createEmailVerification(@RequestBody LinkedHashMap<String, String> body) {
        userService.generateUserVerification(body.get("username"));

        return new ResponseEntity<String>("Verification code generated and email sent", HttpStatus.OK);
    }

    @ExceptionHandler({IncorrectVerificationCodeException.class})
    public ResponseEntity<String> incorrectCodeHandler() {
        return new ResponseEntity<String>("The code provided does not match the verification code", HttpStatus.CONFLICT);
    }

    @PostMapping("/email/verify")
    public User verifyEmail(@RequestBody LinkedHashMap<String, String> body) {

        Long code = Long.parseLong(body.get("code"));
        String username = body.get("username");

        return userService.verifyEmail(username, code);
    }

    @PutMapping("/update/password")
    public User updatePassword(@RequestBody LinkedHashMap<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        return userService.setPassword(username, password);
    }
}
