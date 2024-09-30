package com.spring_react_demo.demo.exception;

public class UserDoesNotExistException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UserDoesNotExistException() {
        super("The user you are looking for does not exist");
    }
}
