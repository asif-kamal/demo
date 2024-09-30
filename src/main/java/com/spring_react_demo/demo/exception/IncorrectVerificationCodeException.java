package com.spring_react_demo.demo.exception;

public class IncorrectVerificationCodeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public IncorrectVerificationCodeException() {
        super("The code passed in did not match the verification code");
    }
}
