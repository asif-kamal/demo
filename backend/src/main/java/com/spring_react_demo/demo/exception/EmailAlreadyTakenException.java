package com.spring_react_demo.demo.exception;

public class EmailAlreadyTakenException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EmailAlreadyTakenException() {
        super("The email provided is already taken");
    }
}