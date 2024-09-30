package com.spring_react_demo.demo.exception;

public class EmailFailedToSendException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EmailFailedToSendException() {
        super("The email failed to send");
    }
}
