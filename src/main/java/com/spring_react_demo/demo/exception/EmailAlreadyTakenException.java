package com.spring_react_demo.demo.exception;

public class EmailAlreadyTakenException extends RuntimeException {

    public EmailAlreadyTakenException() {
        super("The email provided is already taken");
    }
}