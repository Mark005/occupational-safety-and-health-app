package com.nncompany.impl.exception;

public class LoginIsAlreadyExistException extends RuntimeException {
    public LoginIsAlreadyExistException(String message) {
        super(message);
    }
}
